package com.exemplo.saudacao_api.service;

import com.exemplo.saudacao_api.exception.*;
import com.exemplo.saudacao_api.model.dto.CardDTO;
import com.exemplo.saudacao_api.model.entity.CardEntity;
import com.exemplo.saudacao_api.model.entity.ClientEntity;
import com.exemplo.saudacao_api.model.types.*;
import com.exemplo.saudacao_api.repository.CardRepository;
import com.exemplo.saudacao_api.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final ClientRepository clientRepository;

    public CardService(
            CardRepository cardRepository,
            ClientRepository clientRepository
    ) {
        this.cardRepository = cardRepository;
        this.clientRepository = clientRepository;
    }

    public CardDTO createCard(
            String username,
            CardType type,
            CardTier tier,
            CardVariant variant
    ) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new ClientNotFoundException(username));

        var number = generateUniqueCardNumber();
        var cvv = generateCvv();
        var expiration = LocalDate.now().plusYears(4);

        var card = new CardEntity(number, cvv, expiration, type, tier, variant, client);
        var saved = cardRepository.save(card);

        return toDTO(saved);
    }

    public List<CardDTO> getCards(String username) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new ClientNotFoundException(username));

        return cardRepository.findByClient(client)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<CardDTO> getActiveCards(String username) {
        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new ClientNotFoundException(username));

        return cardRepository.findByClientAndStatus(client, CardStatus.ATIVO)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public CardDTO blockCard(Long cardId) {
        var card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        if (card.getStatus() == CardStatus.CANCELADO)
            throw new CardCancelledException();

        card.setStatus(CardStatus.BLOQUEADO);
        return toDTO(cardRepository.save(card));
    }

    public CardDTO cancelCard(Long cardId) {
        var card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        card.setStatus(CardStatus.CANCELADO);
        return toDTO(cardRepository.save(card));
    }

    /**
     * Usa o limite do cartão de crédito.
     * Só funciona em cartões CREDITO e ATIVO.
     * Não permite ultrapassar o limite do tier.
     *
     * @param cardId id do cartão
     * @param value valor da compra
     * @return CardDTO atualizado
     */
    public CardDTO useLimit(Long cardId, Double value) {
        var card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        if (card.getStatus() != CardStatus.ATIVO)
            throw new CardNotActiveException();

        if (card.getType() != CardType.CREDITO)
            throw new InvalidCardTypeException();

        double available = card.getTier().limit - card.getUsedLimit();
        if (value > available)
            throw new InsufficientLimitException();

        card.setUsedLimit(card.getUsedLimit() + value);
        return toDTO(cardRepository.save(card));
    }

    /**
     * Paga a fatura do cartão, devolvendo o limite usado.
     * Não permite pagar mais do que o valor devido.
     *
     * @param cardId id do cartão
     * @param value valor do pagamento
     * @return CardDTO atualizado
     */
    public CardDTO payBill(Long cardId, Double value) {
        var card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        if (value <= 0)
            throw new InvalidAmountException();

        if (value > card.getUsedLimit())
            throw new InvalidBillPaymentException();

        card.setUsedLimit(card.getUsedLimit() - value);
        return toDTO(cardRepository.save(card));
    }

    private String generateUniqueCardNumber() {
        var random = new Random();
        String number;

        do {
            number = String.format("%04d %04d %04d %04d",
                    random.nextInt(10000),
                    random.nextInt(10000),
                    random.nextInt(10000),
                    random.nextInt(10000)
            );
        } while (cardRepository.existsByNumber(number));

        return number;
    }

    private String generateCvv() {
        return String.format("%03d", new Random().nextInt(1000));
    }

    private CardDTO toDTO(CardEntity card) {
        double available = card.getTier().limit - card.getUsedLimit();
        return new CardDTO(
                card.getNumber(),
                card.getType(),
                card.getTier(),
                card.getVariant(),
                card.getStatus(),
                card.getUsedLimit(),
                available,
                card.getExpirationDate(),
                card.getClient().getUsername()
        );
    }
}
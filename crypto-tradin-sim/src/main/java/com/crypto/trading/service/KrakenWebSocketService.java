package com.crypto.trading.service;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.PostConstruct;

import java.net.URI;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@ClientEndpoint
public class KrakenWebSocketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KrakenWebSocketService.class);
    private static final String WEBSOCKET_URL = "wss://ws.kraken.com/";
    private Session session;
    private final ConcurrentHashMap<String, BigDecimal> cryptoPrices = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        connect();
    }

    public void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(WEBSOCKET_URL));
            LOGGER.info("WebSocket connection initialized.");
        } catch (Exception e) {
            LOGGER.error("WebSocket connection error: {}", e.getMessage(), e);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        LOGGER.info("Connected to Kraken WebSocket");
        subscribeTo50Cryptos();
    }

    @OnMessage
    public void onMessage(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            if (jsonNode.isArray() && jsonNode.size() > 3) {
                String pair = jsonNode.get(jsonNode.size() - 1).asText();
                String priceString = jsonNode.get(1).get("c").get(0).asText();
                BigDecimal price = new BigDecimal(priceString);

                cryptoPrices.put(pair, price);
            }
        } catch (Exception e) {
            LOGGER.error("Error processing WebSocket message: {}", e.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        LOGGER.warn("WebSocket closed: {}. Reconnecting...", reason);
        try {
            Thread.sleep(5000);
            connect();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void subscribeTo50Cryptos() {
        String request = """
            {
                "event": "subscribe",
                "pair": [
                    "BTC/USD", "ETH/USD", "BNB/USD", "ADA/USD", "SOL/USD", 
                    "XRP/USD", "DOT/USD", "DOGE/USD", "LTC/USD", "LINK/USD",
                    "AVAX/USD", "MATIC/USD", "UNI/USD", "SHIB/USD", "LUNA/USD",
                    "ALGO/USD", "ATOM/USD", "VET/USD", "AAVE/USD", "FIL/USD",
                    "XTZ/USD", "FTM/USD", "TRX/USD", "XLM/USD", "EOS/USD",
                    "CAKE/USD", "XMR/USD", "BCH/USD", "DASH/USD", "ZEC/USD",
                    "SUSHI/USD", "MKR/USD", "COMP/USD", "SNX/USD", "ENJ/USD",
                    "CHZ/USD", "BAT/USD", "QTUM/USD", "EGLD/USD", "CEL/USD",
                    "HBAR/USD", "KSM/USD", "XEM/USD", "REN/USD", "AR/USD",
                    "HNT/USD", "MANA/USD", "GRT/USD", "FLOW/USD", "STX/USD"
                ],
                "subscription": { "name": "ticker" }
            }
            """;

        session.getAsyncRemote().sendText(request);
        LOGGER.info("Subscribed to 50 cryptocurrencies.");
    }

    public BigDecimal getLatestPrice(String symbol) {
        if (!cryptoPrices.containsKey(symbol + "/USD")) {
            throw new NoSuchElementException("not supported now");
        }

        return cryptoPrices.getOrDefault(symbol + "/USD", BigDecimal.ZERO);
    }
}
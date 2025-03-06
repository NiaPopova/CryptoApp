# Crypto Trading App

## Overview
This is a cryptocurrency trading application built using Java and SpringBoot. It provides functionalities for trading, user management, and handling transactions.

## Features
- **User Management:** Users can login and view their balance or reset it.
- **Crypto Trading:** Buy and sell cryptocurrencies.
- **Transaction History:** View past transactions.
- **Error Handling:** Custom exceptions for better error management.

## API Endpoints
### User Endpoints
- `POST /user/account/login` - Authenticate user.
- `GET  /user/account/balance` - Check user balance.

### Transaction Endpoints
- `GET /transactions/allTransactions` - Get all transactions made by user.
- `POST /transactions/buy` - Buy a cryptocurrency.
- `POST /transactions/sell` - Sell a cryptocurrency.

### User Holdings Endpoints
- `GET /user/hold/allHolds` - Get all the holding of user.

### Crypto Currency Endpoints
- `GET /crypto/top-20` - Get top 20 cryptocurrencies.

## Exception Handling
The application includes custom exception handling and custom exceptions such as:
- `AssetNotOwnedException`
- `InsufficientBalanceException`
- `UnauthorizedException`

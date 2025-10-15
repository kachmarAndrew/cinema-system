-- ============================================
-- DDL: Cinema Database Schema
-- Author: Andrii Kachmar
-- ============================================

-- Drop tables if they already exist.
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS tickets CASCADE;
DROP TABLE IF EXISTS sessions CASCADE;
DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ============================================
-- 1️⃣ USERS
-- ============================================
CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    role     VARCHAR(20)         NOT NULL,
    balance  DECIMAL(10, 2)      NOT NULL DEFAULT 0.00
);

-- ============================================
-- 2️⃣ FILMS
-- ============================================
CREATE TABLE films
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    genre      VARCHAR(50)  NOT NULL,
    language   VARCHAR(50)  NOT NULL DEFAULT 'Ukrainian',
    release_at TIMESTAMP    NOT NULL,
    end_at     TIMESTAMP
);

-- ============================================
-- 3️⃣ SESSIONS
-- ============================================
CREATE TABLE sessions
(
    id                BIGSERIAL PRIMARY KEY,
    film_id           BIGINT    NOT NULL REFERENCES films (id) ON DELETE CASCADE,
    quantity_of_seats INTEGER   NOT NULL CHECK (quantity_of_seats > 0),
    start_at          TIMESTAMP NOT NULL,
    end_at            TIMESTAMP NOT NULL
);

-- ============================================
-- 4️⃣ TICKETS
-- ============================================
CREATE TABLE tickets
(
    id            BIGSERIAL PRIMARY KEY,
    session_id    BIGINT         NOT NULL REFERENCES sessions (id) ON DELETE CASCADE,
    user_id       BIGINT         REFERENCES users (id) ON DELETE SET NULL,
    hall_number   INTEGER,
    row_number    INTEGER        NOT NULL,
    seat_number   INTEGER        NOT NULL,
    ticket_status VARCHAR(20)    NOT NULL,
    price         DECIMAL(10, 2) NOT NULL,
    created_at    TIMESTAMP               DEFAULT NOW(),
    CONSTRAINT unique_seat UNIQUE (session_id, row_number, seat_number)
);

-- ============================================
-- 5️⃣ ORDERS
-- ============================================
CREATE TABLE orders
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    total   DECIMAL(10, 2) NOT NULL,
    paid_at TIMESTAMP
);

-- ============================================
-- 6️⃣ ORDER_ITEMS
-- ============================================
CREATE TABLE order_items
(
    id        BIGSERIAL PRIMARY KEY,
    order_id  BIGINT         NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    ticket_id BIGINT         NOT NULL REFERENCES tickets (id) ON DELETE CASCADE,
    price     DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    CONSTRAINT unique_order_ticket UNIQUE (order_id, ticket_id)
);

-- ============================================
-- 7️⃣ REVIEWS -> to films
-- ============================================
CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    film_assessment VARCHAR(20) NOT NULL,
    user_name VARCHAR(20) NOT NULL,
    description VARCHAR(255) NOT NULL,
    film_id BIGINT NOT NULL REFERENCES films (id) ON DELETE CASCADE
);

-- ============================================
-- Indexes (optional -> for performance)
-- ============================================
CREATE INDEX idx_tickets_session_id ON tickets (session_id);
CREATE INDEX idx_tickets_user_id ON tickets (user_id);
CREATE INDEX idx_sessions_film_id ON sessions (film_id);
CREATE INDEX idx_order_items_order_id ON order_items (order_id);
CREATE INDEX idx_order_items_ticket_id ON order_items (ticket_id);
CREATE INDEX idx_orders_user_id ON orders (user_id);

CREATE TABLE player (
 id uuid PRIMARY KEY NOT NULL,
 username varchar(128) NOT NULL,
 password varchar(40) NOT NULL
);

CREATE TABLE transactions (
    id uuid PRIMARY KEY NOT NULL,
    type varchar(40) NOT NULL,
    date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(19,4) NOT NULL,
    account_id uuid NOT NULL,

    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
        REFERENCES account(id)
);


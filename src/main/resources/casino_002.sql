CREATE TABLE account (
 id uuid PRIMARY KEY NOT NULL,
 balance DECIMAL(19,4),
 player_id uuid NOT NULL,

 CONSTRAINT fk_player
      FOREIGN KEY (player_id)
      REFERENCES player(id)
);
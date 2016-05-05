CREATE TABLE notifications (
    id serial PRIMARY KEY,
    recipient text NOT NULL,
    subject text,
    message text NOT NULL,
    sent boolean DEFAULT false
);

ALTER TABLE notifications OWNER TO postgres;

CREATE TABLE IF NOT EXISTS dataset (
    variant_number INT PRIMARY KEY,
    I1 VARCHAR(255),
    I2 VARCHAR(255),
    P1 VARCHAR(255),
    P2 VARCHAR(255),
    Q VARCHAR(255),
    MoE1 VARCHAR(255),
    MoE2 VARCHAR(255)
    );

INSERT INTO dataset (variant_number, I1, I2, P1, P2, Q, MoE1, MoE2) VALUES
                                                                         (0, 'Exponential (2)', 'Erlang (2,3)', 'Normal (1, 0.2)', 'Erlang (2, 8)', 'FIFO', 'Load factor', 'Max. of all jobs in queue'),
                                                                         (1, 'Exponential (1.5)', 'Exponential (4)', 'Normal (2, 0.3)', 'Normal (2.5, 0.5)', 'FIFO', 'Downtime factor', 'Average of jobs in queue'),
                                                                         (2, 'Erlang (2, 3)', 'Normal (2, 2)', 'Normal (2, 1.5)', 'Normal (1.5, 0.5)', 'LIFO', 'Downtime factor', 'Max. of all jobs in queue'),
                                                                         (3, 'Exponential (1.5)', 'Exponential (4)', 'Normal (2, 0.3)', 'Normal (2.5, 0.5)', 'LIFO', 'Downtime factor', 'Average of jobs in queue'),
                                                                         (4, 'Exponential (1.4)', 'Erlang (3,7)', 'Normal (1, 0.2)', 'Erlang (2, 1)', 'FIFO', 'Load factor', 'Max. of all jobs in queue'),
                                                                         (5, 'Exponential (2.4)', 'Erlang (3, 7)', 'Normal (3, 1.1)', 'Erlang (4, 1)', 'FIFO', 'Load factor', 'Average of jobs in queue'),
                                                                         (6, 'Exponential (1.5)', 'Exponential (4)', 'Normal (2, 0.3)', 'Normal (2.5, 0.5)', 'FIFO', 'Downtime factor', 'Max. of all jobs in queue'),
                                                                         (7, 'Erlang (2, 3)', 'Normal (2, 2)', 'Normal (2, 1.5)', 'Normal (1.5, 0.5)', 'LIFO', 'Downtime factor', 'Average of jobs in queue'),
                                                                         (8, 'Exponential (1.5)', 'Exponential (4)', 'Normal (2, 0.3)', 'Normal (2.5, 0.5)', 'FIFO', 'Downtime factor', 'Max. of all jobs in queue'),
                                                                         (9, 'Exponential (1.4)', 'Erlang (3, 7)', 'Normal (1, 0.2)', 'Erlang (2, 1)', 'FIFO', 'Load factor', 'Average of jobs in queue');

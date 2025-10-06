create table if not exists challenges (
    id serial primary key,
    title varchar(100) not null,
    description varchar(2000) not null,
    start_date timestamptz,
    end_date timestamptz,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    is_active boolean not null
);

create table if not exists roles (
    id serial primary key,
    name varchar(10) unique not null
);

create table if not exists users (
    id serial primary key,
    email varchar(50) unique not null,
    password varchar(100) not null,
    first_name varchar(20),
    last_name varchar(50),
    created_at timestamptz not null,
    updated_at timestamptz not null,
    is_active boolean not null
);

create table if not exists user_roles (
    user_id INTEGER references users(id),
    role_id INTEGER references roles(id),
    primary key (user_id, role_id)
);

create table if not exists teams (
    id serial primary key,
    team_admin INTEGER references users(id),
    name varchar(20) not null,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    is_active boolean not null
);

create table if not exists team_members (
    team_id INTEGER references teams(id),
    email varchar(50) not null,
    first_name varchar(20),
    last_name varchar(50),
    primary key (team_id, email)
);

create table if not exists submissions (
    id serial primary key,
    challenge_id INTEGER references challenges(id),
    team_id INTEGER references teams(id),
    similarity numeric(4,2),
    unique (challenge_id, team_id)
);
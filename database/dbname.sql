--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: alarmed_firefighters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alarmed_firefighters (
    alarm_id integer NOT NULL,
    firefighter_id integer NOT NULL,
    accepted boolean
);


ALTER TABLE public.alarmed_firefighters OWNER TO postgres;

--
-- Name: alarms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alarms (
    alarm_id integer NOT NULL,
    alarm_city character varying(255) NOT NULL,
    alarm_street character varying(255) NOT NULL,
    alarm_description character varying(255) NOT NULL,
    date_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.alarms OWNER TO postgres;

--
-- Name: alarms_alarm_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alarms_alarm_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.alarms_alarm_id_seq OWNER TO postgres;

--
-- Name: alarms_alarm_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alarms_alarm_id_seq OWNED BY public.alarms.alarm_id;


--
-- Name: fire_departments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fire_departments (
    department_id integer NOT NULL,
    department_name character varying(255) NOT NULL,
    department_city character varying(255) NOT NULL,
    department_street character varying(255) NOT NULL,
    department_latitude numeric(10,8) NOT NULL,
    department_longitude numeric(10,8) NOT NULL
);


ALTER TABLE public.fire_departments OWNER TO postgres;

--
-- Name: fire_departments_department_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fire_departments_department_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.fire_departments_department_id_seq OWNER TO postgres;

--
-- Name: fire_departments_department_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fire_departments_department_id_seq OWNED BY public.fire_departments.department_id;


--
-- Name: fire_engines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fire_engines (
    vehicle_id integer NOT NULL,
    department_id integer,
    vehicle_brand character varying(255),
    vehicle_model character varying(255),
    vehicle_registration_number character varying(20),
    vehicle_type character varying(50),
    vehicle_seat_capacity integer
);


ALTER TABLE public.fire_engines OWNER TO postgres;

--
-- Name: fire_engines_vehicle_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fire_engines_vehicle_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.fire_engines_vehicle_id_seq OWNER TO postgres;

--
-- Name: fire_engines_vehicle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fire_engines_vehicle_id_seq OWNED BY public.fire_engines.vehicle_id;


--
-- Name: firefighters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.firefighters (
    firefighter_id integer NOT NULL,
    department_id integer NOT NULL,
    firefighter_name character varying(255) NOT NULL,
    firefighter_username character varying(255) NOT NULL,
    firefighter_commander boolean NOT NULL,
    firefighter_driver boolean NOT NULL,
    firefighter_technical_rescue boolean NOT NULL
);


ALTER TABLE public.firefighters OWNER TO postgres;

--
-- Name: firefighters_firefighter_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.firefighters_firefighter_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.firefighters_firefighter_id_seq OWNER TO postgres;

--
-- Name: firefighters_firefighter_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.firefighters_firefighter_id_seq OWNED BY public.firefighters.firefighter_id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(16) NOT NULL,
    password character varying(32) NOT NULL,
    role character varying(255) DEFAULT 'USER'::character varying NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: alarms alarm_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alarms ALTER COLUMN alarm_id SET DEFAULT nextval('public.alarms_alarm_id_seq'::regclass);


--
-- Name: fire_departments department_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fire_departments ALTER COLUMN department_id SET DEFAULT nextval('public.fire_departments_department_id_seq'::regclass);


--
-- Name: fire_engines vehicle_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fire_engines ALTER COLUMN vehicle_id SET DEFAULT nextval('public.fire_engines_vehicle_id_seq'::regclass);


--
-- Name: firefighters firefighter_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.firefighters ALTER COLUMN firefighter_id SET DEFAULT nextval('public.firefighters_firefighter_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: alarmed_firefighters; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alarmed_firefighters (alarm_id, firefighter_id, accepted) FROM stdin;
1	1	t
1	2	t
1	3	t
1	4	f
1	5	t
1	6	\N
1	9	t
1	27	t
1	28	f
1	29	t
1	30	t
1	31	f
1	32	\N
2	1	f
2	27	f
2	28	t
2	29	t
2	30	t
2	31	t
2	32	t
3	1	\N
3	27	\N
3	28	\N
3	29	\N
3	31	\N
3	32	\N
4	1	\N
4	27	\N
4	28	\N
4	29	\N
4	31	\N
4	32	\N
5	1	\N
5	27	\N
5	28	\N
5	29	\N
5	31	\N
5	32	\N
6	1	\N
6	27	\N
6	28	\N
6	29	\N
6	31	\N
6	32	\N
7	1	\N
7	27	\N
7	28	\N
7	29	\N
7	31	\N
7	32	\N
8	1	t
8	27	\N
8	28	\N
8	29	\N
8	31	\N
8	32	\N
9	1	\N
9	27	\N
9	28	\N
9	29	\N
9	31	\N
9	32	\N
10	1	\N
10	27	\N
10	28	\N
10	29	\N
10	31	\N
10	32	\N
11	1	\N
11	27	\N
11	28	\N
11	29	\N
11	31	\N
11	32	\N
12	1	f
12	27	t
12	28	\N
12	29	\N
12	31	\N
12	32	\N
13	1	t
13	27	\N
13	28	\N
13	29	\N
13	31	\N
13	32	\N
14	1	t
14	27	f
14	28	\N
14	29	\N
14	31	\N
14	32	\N
15	1	t
15	27	t
15	28	\N
15	29	\N
15	31	\N
15	32	\N
16	1	\N
16	27	\N
16	28	\N
16	29	\N
16	31	\N
16	32	\N
17	1	t
17	27	t
17	28	\N
17	29	\N
17	31	\N
17	32	\N
18	1	t
18	27	t
18	28	\N
18	29	\N
18	31	\N
18	32	\N
19	1	t
19	27	t
19	28	\N
19	29	\N
19	31	\N
19	32	\N
\.


--
-- Data for Name: alarms; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.alarms (alarm_id, alarm_city, alarm_street, alarm_description, date_time) FROM stdin;
1	Stargard	Wyszyńskiego 15	Pożar mieszkania w bloku na 2 piętrze	2023-07-17 22:36:33
2	Buczkowice	Bielska 1145	Pożar auta na stacji paliw	2023-07-18 13:42:23
3	Stargard	Kardynała Stefana Wyszyńskiego 10	Bank fire in the server room. Electrical fire, possible victims inside	2023-08-31 12:43:49
4	Stargard	Kardynała Stefana Wyszyńskiego 10	bank fire	2023-08-31 12:49:58
5	Stargard	Kardynała Stefana Wyszyńskiego 10	bank fire	2023-08-31 12:51:00
6	Stargard	Kardynała Stefana Wyszyńskiego 10	fire	2023-08-31 12:52:56
7	Stargard	Kardynała Stefana Wyszyńskiego 10	fire	2023-08-31 12:55:48
8	Stargard	Kardynała Stefana Wyszyńskiego 10	bank fire	2023-08-31 12:58:10
9	Stargard	Kardynała Stefana Wyszyńskiego 10	bank fire	2023-08-31 13:06:13
10	Stargard	Liryczna 10d	asdfasdf	2023-08-31 13:16:04
11	Stargard	Liryczna 10h	uszy sie pala	2023-08-31 13:19:32
12	Stargard	Liryczna 10h	dach sie pali	2023-08-31 13:26:20
13	Stargard	Kardynała Stefana Wyszyńskiego 10	pozar	2023-08-31 13:39:23
14	Stargard	Kardynała Stefana Wyszyńskiego 10	pozar	2023-08-31 13:47:39
15	Stargard	Kardynała Stefana Wyszyńskiego 10	fire	2023-08-31 14:01:34
16	73-134	gmina Stargard Barzkowice	Trash can fire	2023-09-12 23:11:52
17	województwo zachodniopomorskie	Barzkowice 13	Trash can fiire	2023-09-12 23:16:21
18	Stargard	Liryczna 10d	house fire	2023-09-12 23:28:26
19	Stargard	Liryczna 10d	house fire	2023-09-12 23:32:45
\.


--
-- Data for Name: fire_departments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fire_departments (department_id, department_name, department_city, department_street, department_latitude, department_longitude) FROM stdin;
1	Komenda Powiatowa Państwowej Straży Pożarnej w Stargardzie	Stargard	Księcia Bogusława IV 21	53.33345505	15.03770277
2	Ochotnicza Straż Pożarna w Grzędzicach	Grzędzice	Jeziorna 28	53.36963555	14.97373476
3	Ochotnicza Straż Pożarna w Żarowie	Żarowo	Długa 45	53.38497450	14.99095753
4	Ochotnicza Straż Pożarna w Barzkowicach	Barzkowice	26	53.34043325	15.26664752
\.


--
-- Data for Name: fire_engines; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fire_engines (vehicle_id, department_id, vehicle_brand, vehicle_model, vehicle_registration_number, vehicle_type, vehicle_seat_capacity) FROM stdin;
\.


--
-- Data for Name: firefighters; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.firefighters (firefighter_id, department_id, firefighter_name, firefighter_username, firefighter_commander, firefighter_driver, firefighter_technical_rescue) FROM stdin;
1	4	Mateusz Drogosz	mdrogosz	f	f	f
2	1	Adam Nowak	anowak	t	t	t
3	1	Piotr Kowalski	pkowalski	f	t	f
4	1	Jan Wiśniewski	jwisniewski	f	t	f
5	1	Andrzej Dąbrowski	adabrowski	f	f	t
6	1	Paweł Lewandowski	plewandowski	f	f	t
9	1	Krzysztof Wójcik	kwojcik	f	f	f
10	2	Tomasz Kamiński	tkaminski	t	t	f
11	2	Michał Kowalczyk	mkowalczyk	f	f	t
12	2	Marcin Zieliński	mzielinski	f	t	f
13	2	Grzegorz Szymański	gszymanski	f	f	f
17	2	Jerzy Woźniak	jwozniak	f	f	f
18	2	Rafał Kozłowski	rkozlowski	f	t	t
19	3	Dariusz Jankowski	djankowski	t	f	f
20	3	Marek Mazur	mmazur	f	t	t
21	3	Jacek Krawczyk	jkrawczyk	f	f	t
22	3	Maciej Piotrowski	mpiotrowski	f	f	f
23	3	Ireneusz Grabowski	igrabowski	f	t	f
25	3	Radosław Nowakowski	rnowakowski	f	f	f
27	4	Marek Drogosz	marekdrogosz	t	t	f
28	4	Marcel Dębicz	mdebicz	f	f	f
29	4	Michał Kwiatkowski	mkwiatkowski	f	t	f
31	4	Mateusz Antoniewicz	mantoniewicz	f	t	t
32	4	Szymon Deszkiewicz	sdeszkiewicz	f	f	t
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, password, role) FROM stdin;
1	adam	mickiewicz	USER
2	123	123	USER
3	mdrogosz	barzkowice	USER
4	anowak	anowak	USER
5	pkowalski	pkowalski	USER
6	jwisniewski	jwisniewski	USER
7	adabrowski	adabrowski	USER
8	plewandowski	plewandowski	USER
9	kwojcik	kwojcik	USER
10	tkaminski	tkaminski	USER
11	mkowalczyk	mkowalczyk	USER
12	mzielinski	mzielinski	USER
13	gszymanski	gszymanski	USER
14	jwozniak	jwozniak	USER
15	rkozlowski	rkozlowski	USER
16	djankowski	djankowski	USER
17	mmazur	mmazur	USER
18	jkrawczyk	jkrawczyk	USER
19	mpiotrowski	mpiotrowski	USER
20	igrabowski	igrabowski	USER
21	rnowakowski	rnowakowski	USER
22	marekdrogosz	marekdrogosz	USER
23	mdebicz	mdebicz	USER
24	mkwiatkowski	mkwiatkowski	USER
25	mantoniewicz	mantoniewicz	USER
26	sdeszkiewicz	sdeszkiewicz	USER
27	admin	admin	ADMIN
\.


--
-- Name: alarms_alarm_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.alarms_alarm_id_seq', 20, false);


--
-- Name: fire_departments_department_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fire_departments_department_id_seq', 5, false);


--
-- Name: fire_engines_vehicle_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fire_engines_vehicle_id_seq', 1, false);


--
-- Name: firefighters_firefighter_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.firefighters_firefighter_id_seq', 25, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 28, false);


--
-- Name: fire_departments department_name_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fire_departments
    ADD CONSTRAINT department_name_unique UNIQUE (department_name);


--
-- Name: firefighters firefighter_username_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.firefighters
    ADD CONSTRAINT firefighter_username_unique UNIQUE (firefighter_username);


--
-- Name: alarmed_firefighters pk_alarmed_firefighters; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alarmed_firefighters
    ADD CONSTRAINT pk_alarmed_firefighters PRIMARY KEY (alarm_id, firefighter_id);


--
-- Name: alarms pk_alarms; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alarms
    ADD CONSTRAINT pk_alarms PRIMARY KEY (alarm_id);


--
-- Name: fire_departments pk_fire_departments; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fire_departments
    ADD CONSTRAINT pk_fire_departments PRIMARY KEY (department_id);


--
-- Name: fire_engines pk_fire_engines; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fire_engines
    ADD CONSTRAINT pk_fire_engines PRIMARY KEY (vehicle_id);


--
-- Name: firefighters pk_firefighters; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.firefighters
    ADD CONSTRAINT pk_firefighters PRIMARY KEY (firefighter_id);


--
-- Name: users pk_users; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT pk_users PRIMARY KEY (id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: alarmed_firefighters fk_alarm_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alarmed_firefighters
    ADD CONSTRAINT fk_alarm_id FOREIGN KEY (alarm_id) REFERENCES public.alarms(alarm_id) NOT VALID;


--
-- Name: fire_engines fk_department_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fire_engines
    ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES public.fire_departments(department_id);


--
-- Name: firefighters fk_department_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.firefighters
    ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES public.fire_departments(department_id);


--
-- Name: firefighters fk_firefighter_username; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.firefighters
    ADD CONSTRAINT fk_firefighter_username FOREIGN KEY (firefighter_username) REFERENCES public.users(username) NOT VALID;


--
-- PostgreSQL database dump complete
--


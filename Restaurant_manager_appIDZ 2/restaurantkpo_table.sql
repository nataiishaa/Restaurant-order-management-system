PGDMP     1    9    
    
        |            restaurantkpo    15.6    15.6                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                        1262    24578    restaurantkpo    DATABASE     o   CREATE DATABASE restaurantkpo WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'C';
    DROP DATABASE restaurantkpo;
                postgres    false            �            1259    24580    dishes    TABLE     �   CREATE TABLE public.dishes (
    id integer NOT NULL,
    cook_time integer,
    description character varying(255),
    name character varying(255),
    price double precision,
    quantity integer
);
    DROP TABLE public.dishes;
       public         heap    postgres    false            �            1259    24579    dishes_id_seq    SEQUENCE     �   CREATE SEQUENCE public.dishes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.dishes_id_seq;
       public          postgres    false    215            !           0    0    dishes_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.dishes_id_seq OWNED BY public.dishes.id;
          public          postgres    false    214            �            1259    24588    orders    TABLE       CREATE TABLE public.orders (
    id integer NOT NULL,
    status smallint,
    time_to_ready integer,
    user_id integer,
    total_price double precision,
    payment_amount double precision,
    CONSTRAINT orders_status_check CHECK (((status >= 0) AND (status <= 4)))
);
    DROP TABLE public.orders;
       public         heap    postgres    false            �            1259    24608 
   orders_seq    SEQUENCE     t   CREATE SEQUENCE public.orders_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.orders_seq;
       public          postgres    false            �            1259    24595    users    TABLE     �   CREATE TABLE public.users (
    id integer NOT NULL,
    login character varying(255),
    password character varying(255),
    role smallint,
    CONSTRAINT users_role_check CHECK (((role >= 0) AND (role <= 1)))
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    24594    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    218            "           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    217            y           2604    24583 	   dishes id    DEFAULT     f   ALTER TABLE ONLY public.dishes ALTER COLUMN id SET DEFAULT nextval('public.dishes_id_seq'::regclass);
 8   ALTER TABLE public.dishes ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    214    215            z           2604    24598    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    218    218                      0    24580    dishes 
   TABLE DATA           S   COPY public.dishes (id, cook_time, description, name, price, quantity) FROM stdin;
    public          postgres    false    215   g                 0    24588    orders 
   TABLE DATA           a   COPY public.orders (id, status, time_to_ready, user_id, total_price, payment_amount) FROM stdin;
    public          postgres    false    216                    0    24595    users 
   TABLE DATA           :   COPY public.users (id, login, password, role) FROM stdin;
    public          postgres    false    218   N       #           0    0    dishes_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.dishes_id_seq', 5, true);
          public          postgres    false    214            $           0    0 
   orders_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.orders_seq', 451, true);
          public          postgres    false    219            %           0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 1, false);
          public          postgres    false    217            ~           2606    24587    dishes dishes_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.dishes
    ADD CONSTRAINT dishes_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.dishes DROP CONSTRAINT dishes_pkey;
       public            postgres    false    215            �           2606    24593    orders orders_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_pkey;
       public            postgres    false    216            �           2606    24605 #   dishes uk_g9v3f8f18je2t2ou8fvwse3kq 
   CONSTRAINT     ^   ALTER TABLE ONLY public.dishes
    ADD CONSTRAINT uk_g9v3f8f18je2t2ou8fvwse3kq UNIQUE (name);
 M   ALTER TABLE ONLY public.dishes DROP CONSTRAINT uk_g9v3f8f18je2t2ou8fvwse3kq;
       public            postgres    false    215            �           2606    24607 "   users uk_ow0gan20590jrb00upg3va2fn 
   CONSTRAINT     ^   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_ow0gan20590jrb00upg3va2fn UNIQUE (login);
 L   ALTER TABLE ONLY public.users DROP CONSTRAINT uk_ow0gan20590jrb00upg3va2fn;
       public            postgres    false    218            �           2606    24603    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    218               �   x�%���0Dg�+̎P�R	f&6&&+�Ԉ����~=�Nz���5�;�%.%���#Mq]��h=�l*�H�V3KJL�)r��䅌�-��W�7���3t0�I&�߸���K�4LMaR���_��F��~��2����-�:|����6         2   x�365�4�4�4�446����210rQ�9�QLP�M�Q͈���� uq�            x�3�LL��̃��\1z\\\ B�B     
PGDMP         
                x            Shop    12.3    12.3     4           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            5           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            6           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            7           1262    16393    Shop    DATABASE     �   CREATE DATABASE "Shop" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Russian_Russia.1251' LC_CTYPE = 'Russian_Russia.1251';
    DROP DATABASE "Shop";
                postgres    false            �            1259    16394    Buyers    TABLE     f   CREATE TABLE public."Buyers" (
    name text,
    "lastName" text NOT NULL,
    id bigint NOT NULL
);
    DROP TABLE public."Buyers";
       public         heap    postgres    false            �            1259    16429    Buyers_id_seq    SEQUENCE     �   ALTER TABLE public."Buyers" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Buyers_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    232            �            1259    16408    Products    TABLE     K   CREATE TABLE public."Products" (
    price real,
    name text NOT NULL
);
    DROP TABLE public."Products";
       public         heap    postgres    false            �            1259    16411 	   Purchases    TABLE     h   CREATE TABLE public."Purchases" (
    buyer_id bigint NOT NULL,
    date date,
    product_name text
);
    DROP TABLE public."Purchases";
       public         heap    postgres    false            .          0    16394    Buyers 
   TABLE DATA           8   COPY public."Buyers" (name, "lastName", id) FROM stdin;
    public          postgres    false    232          /          0    16408    Products 
   TABLE DATA           1   COPY public."Products" (price, name) FROM stdin;
    public          postgres    false    233   ]       0          0    16411 	   Purchases 
   TABLE DATA           C   COPY public."Purchases" (buyer_id, date, product_name) FROM stdin;
    public          postgres    false    234   �       8           0    0    Buyers_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public."Buyers_id_seq"', 16, true);
          public          postgres    false    235            �
           2606    16446    Buyers buyer_id 
   CONSTRAINT     \   ALTER TABLE ONLY public."Buyers"
    ADD CONSTRAINT buyer_id PRIMARY KEY (id) INCLUDE (id);
 ;   ALTER TABLE ONLY public."Buyers" DROP CONSTRAINT buyer_id;
       public            postgres    false    232    232            �
           2606    16462    Products name 
   CONSTRAINT     ^   ALTER TABLE ONLY public."Products"
    ADD CONSTRAINT name PRIMARY KEY (name) INCLUDE (name);
 9   ALTER TABLE ONLY public."Products" DROP CONSTRAINT name;
       public            postgres    false    233    233            �
           1259    16454    fki_buyer_id    INDEX     H   CREATE INDEX fki_buyer_id ON public."Purchases" USING btree (buyer_id);
     DROP INDEX public.fki_buyer_id;
       public            postgres    false    234            �
           1259    16471    fki_product_name    INDEX     P   CREATE INDEX fki_product_name ON public."Purchases" USING btree (product_name);
 $   DROP INDEX public.fki_product_name;
       public            postgres    false    234            �
           2606    16449    Purchases buyer_id    FK CONSTRAINT     �   ALTER TABLE ONLY public."Purchases"
    ADD CONSTRAINT buyer_id FOREIGN KEY (buyer_id) REFERENCES public."Buyers"(id) NOT VALID;
 >   ALTER TABLE ONLY public."Purchases" DROP CONSTRAINT buyer_id;
       public          postgres    false    232    2729    234            �
           2606    16466    Purchases product_name    FK CONSTRAINT     �   ALTER TABLE ONLY public."Purchases"
    ADD CONSTRAINT product_name FOREIGN KEY (product_name) REFERENCES public."Products"(name) NOT VALID;
 B   ALTER TABLE ONLY public."Purchases" DROP CONSTRAINT product_name;
       public          postgres    false    234    2731    233            .   ?   x�s�I��t,.)�/�44�rO,+��9�e����&\�e�y� *b��҄$b����� ��,      /   @   x�32���O/-*�27���M,�O-�21�H�+)�240��/NL
p:�&�p��qqq �r�      0   d   x�34�4202�50"����Ң.C$A#l���M8���S��M�E��RSP�L���a�Yc
4	$� -1C��M,�OE69.���� �462     
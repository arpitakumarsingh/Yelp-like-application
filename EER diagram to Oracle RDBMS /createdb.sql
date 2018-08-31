CREATE TABLE BUSINESS_CATEGORY
        (
        bcat_id varchar2(10),
        bact_name varchar2(45),
        sub_cat varchar2(45),
        primary key (bcat_id)
        );

INSERT INTO BUSINESS_CATEGORY VALUES('BCT1', 'Amusement Parks', 'Water Parks');
INSERT INTO BUSINESS_CATEGORY VALUES('BCT2', 'National Parks', 'Wildlife National Parks');
INSERT INTO BUSINESS_CATEGORY VALUES('BCT3', 'Career Counseling', 'Career Counselling for kids');
INSERT INTO BUSINESS_CATEGORY VALUES('BCT4', 'Food and More', 'Ice-cream and Yougurt');
INSERT INTO BUSINESS_CATEGORY VALUES('BCT5', 'Bars', 'Sports bar');
INSERT INTO BUSINESS_CATEGORY VALUES('BCT6', 'Restaurents', 'Ice-cream and Yougurt');
INSERT INTO BUSINESS_CATEGORY VALUES('BCT7', 'Pool Cleaners', 'Swimming pool cleaners');
INSERT INTO BUSINESS_CATEGORY VALUES('BCT8', 'Coffee Shops', 'Cold coffee shops');

CREATE TABLE BUSINESS
        (
        business_id varchar2(10),
        bis_name varchar2(45),
        days_operation varchar2(45),
        ambient_type varchar2(45),
        address varchar2(80),
        bcat_id varchar2(10),
        state varchar2(20),
        primary key(business_id),
        foreign key(bcat_id) references BUSINESS_CATEGORY (bcat_id) ON DELETE CASCADE
        );

INSERT INTO BUSINESS VALUES('B1', 'Big Surf' , 'Mon, Tue, Wed', 'Touristy', '1500 N McClintock Dr, Tempe, AZ 85281' , 'BCT1','AZ');
INSERT INTO BUSINESS VALUES('B2','SMITH THOMSON ' , 'Mon, Tue, Wed, Thu', 'Touristy', '1595 Spring Hill Road Suite 110 Vienna VA 22182' ,  'BCT6','VA');
INSERT INTO BUSINESS VALUES('B3','Bay Area Coffee Shop' , 'Mon, Tue, Wed', 'Touristy', '1522 W. Sam Rayburn Dr. Bonham, CA 95051' , 'BCT8','CA');
INSERT INTO BUSINESS VALUES('B4','China  Coffee Toffee ' , 'Mon, Tue, Wed', 'Touristy', '2570 El Camino Real, Santa Clara, CA 95051' , 'BCT4','CA');
INSERT INTO BUSINESS VALUES('B5','Hastings Water Works' , 'Mon, Tue, Wed', '', '10331 Brecksville Rd. Brecksville, OH 44141' , 'BCT7','OH');
INSERT INTO BUSINESS VALUES('B6','Catch Your Big Break' , 'Mon, Tue, Wed, Thu, Fri, Sat, Sun', '', '2341 Roosevelt Ct Santa Clara, CA 95051' , 'BCT3','CA');
INSERT INTO BUSINESS VALUES('B7','The Cinebar' , 'Mon, Tue, Wed, Thu', '', '2O285 South Western Ave.,Suite #2OO,Torrance,CA 9O5O1' , 'BCT5','CA');
INSERT INTO BUSINESS VALUES('B8','Coffee Bar and Bistro' , 'Mon, Tue, Wed', '', '2585 El Camino Real Santa Clara, CA' , 'BCT5','CA');
INSERT INTO BUSINESS VALUES('B9', 'Hobees' , 'Mon, Tue, Wed, Thu, Fri', '', '90 Skyport Dr San Jose, CA 95110' ,'BCT6','CA');
INSERT INTO BUSINESS VALUES('B10','Cafe Gourmet' , 'Mon, Tue', '', '80 N Market St San Jose, CA 95113' , 'BCT6','CAWY');
INSERT INTO BUSINESS VALUES('B11','Yellow Stone National Park' , 'Mon, Fri, Sat, Sun', '', 'Yellow Stone National Park, WY 82190' ,  'BCT2','WY');
INSERT INTO BUSINESS VALUES('B12','Petrified Forest National Park' , 'Mon, Tue, Sun', '', 'P.O. Box 221 AZ 86028' , 'BCT2','AZ');
INSERT INTO BUSINESS VALUES('B13','Grand Canyon National park' , 'Mon, Sat, Sun', '', 'Highway 64, AZ 86023' , 'BCT2','AZ');
INSERT INTO BUSINESS VALUES('B35','Connecticut Bar' , 'Mon, Thu, Sun', '', '5847 San Felipe, Suite 2400 Houston, TX 77057' ,  'BCT5','TX');
INSERT INTO BUSINESS VALUES('B36','Sherleys Bar and Restaurent' , 'Mon, Thu, Sun', '', '1132 Terry Road, Hartford, CT 06105' ,  'BCT5','CT');
INSERT INTO BUSINESS VALUES('B14','Connecticut Bar and Restaurent' , 'Mon, Wed, Sun', '', '1132 Terry Road, Hartford, CT 06105' , 'BCT5','CT');

CREATE TABLE YELP_USER
        (
        yelp_id varchar2(10),
        email varchar2(45),
        first_name varchar2(45),
        last_name varchar2(45),
        birth_date date,
        birth_place varchar2(45),
        gender varchar2(20),
        friend_list varchar2(20),
        complimented_friendlist varchar2(45),
        checkin_bis varchar2(45),
        age number,
        primary key(yelp_id)
        );

INSERT INTO YELP_USER VALUES('Y1','john@yahoo.com', 'John ', 'Smith', to_date('12/12/92', 'MM-DD-YY'), 'FL', 'M', 'Y2,Y7,Y9', 'Y7, Y9', 'B3, B2, B13, B35, B36, B5, B4, B9',25);
INSERT INTO YELP_USER VALUES('Y2','juan@gmail.com', 'Juan ', 'Carlos', to_date('2/7/88', 'MM-DD-YY'), 'AK', 'M', '', '', 'B10, B5, B11, B2, B36, B35',30);
INSERT INTO YELP_USER VALUES('Y3','Jane@gmail.com', 'Jane ', 'Chapel', to_date('6/1/80', 'MM-DD-YY'), 'IL', 'F', 'Y6,Y11,Y5', 'Y6', '',37);
INSERT INTO YELP_USER VALUES('Y4','adi@yahoo.com', 'Aditya ', 'Awasthi', to_date('4/12/94', 'MM-DD-YY'), 'CA', 'M', 'Y7, Y11', 'Y11', 'B4, B11, B12, B5, B36, B35, B2',23);
INSERT INTO YELP_USER VALUES('Y5','james@hotmail.com', 'James ', 'Williams', to_date('5/5/91', 'MM-DD-YY'), 'NY', 'M', '', '', 'B6, B11, B12, B5, B36, B35, B8',26);
INSERT INTO YELP_USER VALUES('Y6','mike@yahoo.com', 'Mike ', 'Brown', to_date('3/1/88', 'MM-DD-YY'), 'NC', 'M', 'Y2, Y4', 'Y4', 'B36, B11, B2, B3, B13',29);
INSERT INTO YELP_USER VALUES('Y7','bob@yahoo.com', 'Bob', 'Jones', to_date('2/19/70', 'MM-DD-YY'), 'NY', 'M', 'Y1, Y3', 'Y3', 'B13, B35, B36, B11, B2, B3',48);
INSERT INTO YELP_USER VALUES('Y8','wei@gmail.com', 'Wei ', 'Zhang', to_date('3/18/75', 'MM-DD-YY'), 'NV', 'F', 'Y6, Y1', 'Y6', 'B11, B2, B13, B35, B36, B4',42);
INSERT INTO YELP_USER VALUES('Y9','mark@gmail.com', 'Mark ', 'Davis', to_date('11/2/93', 'MM-DD-YY'), 'CA', 'M', 'Y1,Y2', 'Y2', 'B5, B11, B2, B13 , B35, B36',24);
INSERT INTO YELP_USER VALUES('Y10','daniel@yahoo.com', 'Daniel ', 'Garcia', to_date('11/2/93', 'MM-DD-YY'), 'NJ', 'M', '', '', 'B1, B5, B11, B2, B36, B35, B3',33);
INSERT INTO YELP_USER VALUES('Y11','maria@hotmail.com', 'Maria ', 'Rodriguez', to_date('8/12/85', 'MM-DD-YY'), 'CA', 'F', 'Y3, Y5', 'Y5', 'B3, B4 , B1, B5, B11, B2, B36, B35',32);
INSERT INTO YELP_USER VALUES('Y12','freya@yahoo.com', 'Freya ', 'Wilson', to_date('10/5/95', 'MM-DD-YY'), 'NJ', 'F', 'Y11', '11', 'B7, B10, B5, B11, B2, B13 , B35, B36',22);

CREATE TABLE USER_CHECKIN
    	  (
      	yelp_id varchar2(10),
      	checkin_bis varchar2(50),
      	foreign key (yelp_id) references YELP_USER (yelp_id) ON DELETE CASCADE
      	);

INSERT INTO USER_CHECKIN VALUES('Y1', 'B3');
INSERT INTO USER_CHECKIN VALUES('Y1', 'B2');
INSERT INTO USER_CHECKIN VALUES('Y1', 'B13');
INSERT INTO USER_CHECKIN VALUES('Y1', 'B35');
INSERT INTO USER_CHECKIN VALUES('Y1', 'B36');
INSERT INTO USER_CHECKIN VALUES('Y1', 'B5');
INSERT INTO USER_CHECKIN VALUES('Y1', 'B4');
INSERT INTO USER_CHECKIN VALUES('Y1', 'B9');
INSERT INTO USER_CHECKIN VALUES('Y2', 'B10');
INSERT INTO USER_CHECKIN VALUES('Y2', 'B5');
INSERT INTO USER_CHECKIN VALUES('Y2', 'B11');
INSERT INTO USER_CHECKIN VALUES('Y2', 'B2');
INSERT INTO USER_CHECKIN VALUES('Y2', 'B36');
INSERT INTO USER_CHECKIN VALUES('Y2', 'B35');
INSERT INTO USER_CHECKIN VALUES('Y4', 'B4');
INSERT INTO USER_CHECKIN VALUES('Y4', 'B11');
INSERT INTO USER_CHECKIN VALUES('Y4', 'B12');
INSERT INTO USER_CHECKIN VALUES('Y4', 'B5');
INSERT INTO USER_CHECKIN VALUES('Y4', 'B36');
INSERT INTO USER_CHECKIN VALUES('Y4', 'B35');
INSERT INTO USER_CHECKIN VALUES('Y4', 'B2');
INSERT INTO USER_CHECKIN VALUES('Y5', 'B6');
INSERT INTO USER_CHECKIN VALUES('Y5', 'B11');
INSERT INTO USER_CHECKIN VALUES('Y5', 'B12');
INSERT INTO USER_CHECKIN VALUES('Y5', 'B5');
INSERT INTO USER_CHECKIN VALUES('Y5', 'B36');
INSERT INTO USER_CHECKIN VALUES('Y5', 'B35');
INSERT INTO USER_CHECKIN VALUES('Y5', 'B2');
INSERT INTO USER_CHECKIN VALUES('Y6', 'B36');
INSERT INTO USER_CHECKIN VALUES('Y6', 'B11');
INSERT INTO USER_CHECKIN VALUES('Y6', 'B2');
INSERT INTO USER_CHECKIN VALUES('Y6', 'B3');
INSERT INTO USER_CHECKIN VALUES('Y6', 'B13');
INSERT INTO USER_CHECKIN VALUES('Y7', 'B13');
INSERT INTO USER_CHECKIN VALUES('Y7', 'B35');
INSERT INTO USER_CHECKIN VALUES('Y7', 'B36');
INSERT INTO USER_CHECKIN VALUES('Y7', 'B11');
INSERT INTO USER_CHECKIN VALUES('Y7', 'B2');
INSERT INTO USER_CHECKIN VALUES('Y7', 'B3');
INSERT INTO USER_CHECKIN VALUES('Y8', 'B11');
INSERT INTO USER_CHECKIN VALUES('Y8', 'B2');
INSERT INTO USER_CHECKIN VALUES('Y8', 'B13');
INSERT INTO USER_CHECKIN VALUES('Y8', 'B35');
INSERT INTO USER_CHECKIN VALUES('Y8', 'B36');
INSERT INTO USER_CHECKIN VALUES('Y8', 'B4');
INSERT INTO USER_CHECKIN VALUES('Y9', 'B5');
INSERT INTO USER_CHECKIN VALUES('Y9', 'B11');
INSERT INTO USER_CHECKIN VALUES('Y9', 'B2');
INSERT INTO USER_CHECKIN VALUES('Y9', 'B13');
INSERT INTO USER_CHECKIN VALUES('Y9', 'B35');
INSERT INTO USER_CHECKIN VALUES('Y9', 'B36');
INSERT INTO USER_CHECKIN VALUES('Y10', 'B1');
INSERT INTO USER_CHECKIN VALUES('Y10', 'B5');
INSERT INTO USER_CHECKIN VALUES('Y10', 'B11');
INSERT INTO USER_CHECKIN VALUES('Y10', 'B35');
INSERT INTO USER_CHECKIN VALUES('Y10', 'B36');
INSERT INTO USER_CHECKIN VALUES('Y10', 'B2');
INSERT INTO USER_CHECKIN VALUES('Y10', 'B3');
INSERT INTO USER_CHECKIN VALUES('Y11', 'B3');
INSERT INTO USER_CHECKIN VALUES('Y11', 'B4');
INSERT INTO USER_CHECKIN VALUES('Y11', 'B1');
INSERT INTO USER_CHECKIN VALUES('Y11', 'B5');
INSERT INTO USER_CHECKIN VALUES('Y11', 'B11');
INSERT INTO USER_CHECKIN VALUES('Y11', 'B2');
INSERT INTO USER_CHECKIN VALUES('Y11', 'B36');
INSERT INTO USER_CHECKIN VALUES('Y11', 'B35');
INSERT INTO USER_CHECKIN VALUES('Y12', 'B7');
INSERT INTO USER_CHECKIN VALUES('Y12', 'B10');
INSERT INTO USER_CHECKIN VALUES('Y12', 'B5');
INSERT INTO USER_CHECKIN VALUES('Y12', 'B11');
INSERT INTO USER_CHECKIN VALUES('Y12', 'B2');
INSERT INTO USER_CHECKIN VALUES('Y12', 'B13');
INSERT INTO USER_CHECKIN VALUES('Y12', 'B35');
INSERT INTO USER_CHECKIN VALUES('Y12', 'B36');

CREATE TABLE COMP_FRIEND
        (
        yelp_id varchar2(10),
        complimented_friendlist varchar2(30),
        foreign key (yelp_id) references YELP_USER(yelp_id) ON DELETE CASCADE
        );

INSERT INTO COMP_FRIEND VALUES('Y1', 'Y7');
INSERT INTO COMP_FRIEND VALUES('Y1', 'Y9');
INSERT INTO COMP_FRIEND VALUES('Y3', 'Y6');
INSERT INTO COMP_FRIEND VALUES('Y4', 'Y11');
INSERT INTO COMP_FRIEND VALUES('Y6', 'Y4');
INSERT INTO COMP_FRIEND VALUES('Y7', 'Y3');
INSERT INTO COMP_FRIEND VALUES('Y8', 'Y6');
INSERT INTO COMP_FRIEND VALUES('Y9', 'Y2');
INSERT INTO COMP_FRIEND VALUES('Y11', 'Y5');
INSERT INTO COMP_FRIEND VALUES('Y12', 'Y11');

CREATE TABLE REVIEWS
      	(
      	review_id varchar2(20),
      	stars number,
      	author varchar2(20),
      	publish_date varchar(100),
      	business_id varchar2(10),
      	comments varchar2(50),
      	primary key(review_id),
      	foreign key(author) references YELP_USER (yelp_id) ON DELETE CASCADE,
      	foreign key(business_id) references BUSINESS (business_id) ON DELETE CASCADE
      	);

INSERT INTO REVIEWS VALUES('R1', 3, 'Y11', to_date('10/02/07', 'MM-DD-YY'), 'B3',  '');
INSERT INTO REVIEWS VALUES('R2', 2, 'Y10', to_date('09/29/07', 'MM-DD-YY'), 'B1',  'Y3, Y2');
INSERT INTO REVIEWS VALUES('R3', 4, 'Y2', to_date('09/29/07', 'MM-DD-YY'), 'B10', 'Y3');
INSERT INTO REVIEWS VALUES('R4', 5, 'Y11', to_date('10/02/07', 'MM-DD-YY'), 'B4', '');
INSERT INTO REVIEWS VALUES('R5', 5, 'Y1', to_date('10/25/07', 'MM-DD-YY'), 'B3', '');
INSERT INTO REVIEWS VALUES('R6', 5, 'Y4', to_date('09/26/07', 'MM-DD-YY'), 'B4', '');
INSERT INTO REVIEWS VALUES('R7', 5, 'Y9', to_date('09/26/07', 'MM-DD-YY'), 'B5', 'Y8, Y11');
INSERT INTO REVIEWS VALUES('R8', 5,'Y5', to_date('09/27/07', 'MM-DD-YY'), 'B6', '');
INSERT INTO REVIEWS VALUES('R9', 5,'Y12', to_date('09/28/07', 'MM-DD-YY'), 'B7', '');
INSERT INTO REVIEWS VALUES('R10', 5,'Y5', to_date('10/29/07', 'MM-DD-YY'), 'B8', '');
INSERT INTO REVIEWS VALUES('R11', 5,'Y1', to_date('09/30/07', 'MM-DD-YY'), 'B9', '');
INSERT INTO REVIEWS VALUES('R12', 5,'Y11', to_date('10/25/07', 'MM-DD-YY'), 'B10', '');
INSERT INTO REVIEWS VALUES('R13', 5,'Y4', to_date('09/25/07', 'MM-DD-YY'), 'B11', '');
INSERT INTO REVIEWS VALUES('R14', 5,'Y5', to_date('09/25/07', 'MM-DD-YY'), 'B12', '');
INSERT INTO REVIEWS VALUES('R15', 5,'Y1', to_date('09/29-07', 'MM-DD-YY'), 'B13', 'Y8');
INSERT INTO REVIEWS VALUES('R16', 2,'Y11', to_date('06/07/15', 'MM-DD-YY'), 'B2', 'Y8');
INSERT INTO REVIEWS VALUES('R17', 1,'Y4', to_date('05/05-15', 'MM-DD-YY'), 'B2', '');
INSERT INTO REVIEWS VALUES('R18', 1,'Y1', to_date('05/05/15', 'MM-DD-YY'), 'B2', '');
INSERT INTO REVIEWS VALUES('R19', 1,'Y10', to_date('10/25/07', 'MM-DD-YY'), 'B3', '');
INSERT INTO REVIEWS VALUES('R20', 1,'Y1', to_date('09/28/07', 'MM-DD-YY'), 'B4', 'Y7, Y10');
INSERT INTO REVIEWS VALUES('R21', 4, 'Y11',to_date('06/07/15', 'MM-DD-YY'), 'B14', '');
INSERT INTO REVIEWS VALUES('R22', 1, 'Y4',to_date('05/05/15', 'MM-DD-YY'), 'B14', '');
INSERT INTO REVIEWS VALUES('R23', 1, 'Y1',to_date('05/05/15', 'MM-DD-YY'), 'B14', '');

CREATE TABLE REV_COMMENTS
        (
        review_id varchar2(20),
      	AUTHOR varchar2(30),
        comments varchar2(30),
        foreign key (review_id) references REVIEWS(review_id)
        );

INSERT INTO REV_COMMENTS VALUES('R2', 'Y10', 'Y3');
INSERT INTO REV_COMMENTS VALUES('R2', 'Y10', 'Y2');
INSERT INTO REV_COMMENTS VALUES('R3', 'Y2', 'Y3');
INSERT INTO REV_COMMENTS VALUES('R7', 'Y9', 'Y8');
INSERT INTO REV_COMMENTS VALUES('R7', 'Y9', 'Y11');
INSERT INTO REV_COMMENTS VALUES('R15', 'Y1', 'Y8');
INSERT INTO REV_COMMENTS VALUES('R16', 'Y11', 'Y8');
INSERT INTO REV_COMMENTS VALUES('R20', 'Y1', 'Y7');
INSERT INTO REV_COMMENTS VALUES('R20', 'Y1', 'Y10');

CREATE TABLE CHECK_IN
      	(
      	checkin_id number,
      	info varchar2(30),
      	business_id varchar2(20),
      	primary key (checkin_id)
      	);

INSERT INTO CHECK_IN VALUES('1','Checkin Info 1','B1');
INSERT INTO CHECK_IN VALUES('2','Checkin Info 2','B2');
INSERT INTO CHECK_IN VALUES('3','Checkin Info 3','B3');
INSERT INTO CHECK_IN VALUES('4','Checkin Info 4','B4');
INSERT INTO CHECK_IN VALUES('5','Checkin Info 5','B5');
INSERT INTO CHECK_IN VALUES('6','Checkin Info 6','B6');
INSERT INTO CHECK_IN VALUES('7','Checkin Info 7','B7');
INSERT INTO CHECK_IN VALUES('8','Checkin Info 8','B8');
INSERT INTO CHECK_IN VALUES('9','Checkin Info 9','B9');
INSERT INTO CHECK_IN VALUES('10','Checkin Info 10','B10');
INSERT INTO CHECK_IN VALUES('11','Checkin Info 11','B11');
INSERT INTO CHECK_IN VALUES('12','Checkin Info 12','B12');
INSERT INTO CHECK_IN VALUES('13','Checkin Info 13','B13');

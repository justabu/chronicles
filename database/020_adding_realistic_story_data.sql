alter table stories change column city city varchar(50) not null;

alter table stories change content content text not null;
insert into stories (title,month,year,content,author_user_id,deleted,city) values ('Lauki juice turns killer','July','2010','The road to hell is paved with good intentions. We are forever being advised to do or eat things that will be “beneficial” for our health and while most of these “advices” are in good faith, one should always consult an expert before following them. Sixty-year-old scientist, Sushil Kumar Saxena recently died and his wife Neeraj was hospitalised after they drank a cocktail of lauki (bottle gourd) and karela (bitter gourd) juices not realising that the lauki juice was toxic. Saxena who was the deputy secretary in the Council for Scientific and Industrial Research, died in Rockland Hospital after allegedly vomiting blood. Both he and his wife were diabetics who had been drinking these juices for the last four years after seeing their health benefits on a TV show.',1,false,'Bangalore');


--//@UNDO

delete from stories where title='Lauki juice turns killer';
alter table stories change content content varchar(255) not null;

alter table stories change column city city varchar(50) not null default '';
update stories set city="Bangalore" where id=1;
update stories set city="London" where id=2;
update stories set city="Chicago" where id=3;

--//@UNDO

update stories set city="" where id=1;
update stories set city="" where id=2;
update stories set city="" where id=3;
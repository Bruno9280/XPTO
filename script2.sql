do $$
Declare i int ;
Declare d int ;
Declare tt TEXT;
begin

for  i in 1..1000 loop
if i==3 then
set d= +1;
set tt = Cast(d AS Text);
end if;
INSERT INTO exame_nf(IDNF,NUMERO, DATACADASTRO,TOTALGERAL) VALUES ( i,12,CURRENT_DATE + INTERVAL  tt'day',0);
INSERT INTO exame_itemnf(IDNF, IDPRODUTO,QTDE, VALOR) VALUES ( i,(round(random() * 10000)),round(random() * 100),random()*1000);
INSERT INTO exame_itemnf(IDNF, IDPRODUTO,QTDE, VALOR) VALUES ( i,(round(random() * 10000)),round(random() * 100),random()*1000);
INSERT INTO exame_itemnf(IDNF, IDPRODUTO,QTDE, VALOR) VALUES ( i,(round(random() * 10000)),round(random() * 100),random()*1000);
RAISE NOTICE '%', i;

end loop;
END; 
$$ 
CREATE FUNCTION trigger_function() 
   RETURNS TRIGGER 
   LANGUAGE PLPGSQL
AS $$
BEGIN
   -- trigger logic
END;
$$


do
$$
declare
   x int;
   c cursor for select i from x;
begin
   OPEN c; 
   fetch last from c into x;
   loop
        exit when not found;
        raise notice 'valor de i = %', x; 
        fetch prior from c into x;
   end loop;
   close c;
end; 
$$ language plpgsql;

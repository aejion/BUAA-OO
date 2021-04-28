echo ' ' > check.py
./datacheck_win -i input.txt > std.txt
cat input.txt | ./getpy.exe >> check.py
python check.py
sleep 20
cat std.txt > all.txt
cat output.txt >> all.txt
cat all.txt | ./getans.exe > answer.txt

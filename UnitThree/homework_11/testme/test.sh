while true
do
        ./minpath.exe > input.txt
        cat input.txt | java -jar zhy.jar > zhy.txt
	cat input.txt | java -jar std.jar > lxd.txt
	cat input.txt | java -jar homework3.jar > lth.txt
	cat input.txt | java -jar test1.jar > lyj.txt
	sleep 10
        file1=zhy.txt
        file2=lxd.txt
	file3=lth.txt
	file4=lyj.txt
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
	diff $file1 $file3 > /dev/null
        if [ $? != 0 ]
        then
              break
	fi
	diff $file1 $file4 > /dev/null
        if [ $? != 0 ]
        then
              break
        fi
	./stronglink.exe > input.txt
	cat input.txt | java -jar zhy.jar > zhy.txt
        cat input.txt | java -jar std.jar > lxd.txt
        cat input.txt | java -jar homework3.jar > lth.txt
         cat input.txt | java -jar test1.jar > lyj.txt
        file1=zhy.txt
        file2=lxd.txt
        file3=lth.txt
        file4=lyj.txt
	sleep 10
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
        diff $file1 $file3 > /dev/null
        if [ $? != 0 ]
        then
              break
        fi
	diff $file1 $file4 > /dev/null
        if [ $? != 0 ]
        then
              break
        fi
	./blocksum.exe > input.txt
        cat input.txt | java -jar zhy.jar > zhy.txt
        cat input.txt | java -jar std.jar > lxd.txt
        cat input.txt | java -jar homework3.jar > lth.txt
         cat input.txt | java -jar test1.jar > lyj.txt
        file1=zhy.txt
        file2=lxd.txt
	file3=lth.txt
        file4=lyj.txt
        sleep 10
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
        diff $file1 $file3 > /dev/null
        if [ $? != 0 ]
        then
              break
        fi
        diff $file1 $file4 > /dev/null
        if [ $? != 0 ]
        then
              break
        fi


done
 

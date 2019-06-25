#!/usr/bin bash
echo "-compile start-"

# Compile
javac SortingTestRep.java

mkdir -p ./reptest7/rep_output_3


echo "-execute your program-"
for i in $(seq 7 18)
do
        # 무한루프를 방지하기 위해 input 당 시간제한 1초
	timeout 1000000 java SortingTestRep < ./reptest7/input/$i.txt > ./reptest7/rep_output_3/$i.txt
done

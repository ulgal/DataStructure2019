#!/usr/bin bash
echo "-copy start-"

for i in $(seq 2 10)
do
        cp ./reptest6/input/1.txt ./reptest6/input/$i.txt
done
for i in $(seq 12 20)
do
        cp ./reptest6/input/11.txt ./reptest6/input/$i.txt
done
for i in $(seq 22 30)
do
        cp ./reptest6/input/21.txt ./reptest6/input/$i.txt
done
for i in $(seq 32 40)
do
        cp ./reptest6/input/31.txt ./reptest6/input/$i.txt
done
for i in $(seq 42 50)
do
        cp ./reptest6/input/41.txt ./reptest6/input/$i.txt
done
for i in $(seq 52 60)
do
        cp ./reptest6/input/51.txt ./reptest6/input/$i.txt
done

file = File.new("1.txt", "a+")
file.puts "r 1000000 -3000000 3000000\n"
file.puts "B\nB\nB\nB\nX"
file.close
file = File.new("2.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts i.to_s + "\n"
end
file.puts "B\nB\nB\nB\nX"
file.close
file = File.new("3.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts (1000001 - i).to_s + "\n"
end
file.puts "B\nB\nB\nB\nX"
file.close

file = File.new("4.txt", "a+")
file.puts "r 1000000 -3000000 3000000\n"
file.puts "I\nI\nI\nI\nX"
file.close
file = File.new("5.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts i.to_s + "\n"
end
file.puts "I\nI\nI\nI\nX"
file.close
file = File.new("6.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts (1000001 - i).to_s + "\n"
end
file.puts "I\nI\nI\nI\nX"
file.close

file = File.new("7.txt", "a+")
file.puts "r 1000000 -3000000 3000000\n"
file.puts "H\nH\nH\nH\nX"
file.close
file = File.new("8.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts i.to_s + "\n"
end
file.puts "H\nH\nH\nH\nX"
file.close
file = File.new("9.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts (1000001 - i).to_s + "\n"
end
file.puts "H\nH\nH\nH\nX"
file.close

file = File.new("10.txt", "a+")
file.puts "r 1000000 -3000000 3000000\n"
file.puts "M\nM\nM\nM\nX"
file.close
file = File.new("11.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts i.to_s + "\n"
end
file.puts "M\nM\nM\nM\nX"
file.close
file = File.new("12.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts (1000001 - i).to_s + "\n"
end
file.puts "M\nM\nM\nM\nX"
file.close

file = File.new("13.txt", "a+")
file.puts "r 1000000 -3000000 3000000\n"
file.puts "Q\nQ\nQ\nQ\nX"
file.close
file = File.new("14.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts i.to_s + "\n"
end
file.puts "Q\nQ\nQ\nQ\nX"
file.close
file = File.new("15.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts (1000001 - i).to_s + "\n"
end
file.puts "Q\nQ\nQ\nQ\nX"
file.close

file = File.new("16.txt", "a+")
file.puts "r 1000000 -3000000 3000000\n"
file.puts "R\nR\nR\nR\nX"
file.close
file = File.new("17.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts i.to_s + "\n"
end
file.puts "R\nR\nR\nR\nX"
file.close
file = File.new("18.txt", "a+")
file.puts "1000000\n"
for i in 1..1000000
  file.puts (1000001 - i).to_s + "\n"
end
file.puts "R\nR\nR\nR\nX"
file.close

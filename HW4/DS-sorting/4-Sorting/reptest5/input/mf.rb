file = File.new("1.txt", "a+")
file.puts "100000\n"
for i in 1..100000
  file.puts "2\n"
end
file.puts "B\nB\nB\nB\nX"
file.close
file = File.new("11.txt", "a+")
file.puts "100000\n"
for i in 1..100000
  file.puts "2\n"
end
file.puts "I\nI\nI\nI\nX"
file.close
file = File.new("21.txt", "a+")
file.puts "100000\n"
for i in 1..100000
  file.puts "2\n"
end
file.puts "H\nH\nH\nH\nX"
file.close
file = File.new("31.txt", "a+")
file.puts "100000\n"
for i in 1..100000
  file.puts "2\n"
end
file.puts "M\nM\nM\nM\nX"
file.close
file = File.new("41.txt", "a+")
file.puts "100000\n"
for i in 1..100000
  file.puts "2\n"
end
file.puts "Q\nQ\nQ\nQ\nX"
file.close
file = File.new("51.txt", "a+")
file.puts "100000\n"
for i in 1..100000
  file.puts "2\n"
end
file.puts "R\nR\nR\nR\nX"
file.close

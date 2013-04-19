# Has been written for ruby 1.9.3

require 'open-uri'
require 'zlib'
require 'yajl'
require 'json'

days = (1..31).map { |i| "%02d" % i }
hours = (0..23)

days.each do |day|
  hours.each do |hour|
    file = "http://data.githubarchive.org/2013-03-#{day}-#{hour}.json.gz"
    puts "Retrieving #{file}"
    begin 
      gz = open(file)
      js = Zlib::GzipReader.new(gz).read

      File.open(File.basename(file, ".gz"), 'w') do |f|
          Yajl::Parser.parse(js) do |event|
            f.write(event.to_json)
            f.write("\n") 
          end
      end
    rescue StandardError => e
      puts e
      puts "Error with #{file}"
    end
  end
end
# top-urls
the dataset prepared from: http://carl.cs.indiana.edu/data/websci2014/web-clicks-nov-2009.tgz
downloading the file and using the spark code.
first converted json to csv usign online tool and use the spark to prepare the dataset for the project.

The example spark-shell is given below:

scala> val df = spark.read.format("csv").option("header","true").load("1.csv")
df: org.apache.spark.sql.DataFrame = [count: string, timestamp: string ... 2 more fields]

scala> val filterDF = df.select($"to".alias("url"),$"count".alias("impression_count"),$"timestamp")
filterDF: org.apache.spark.sql.DataFrame = [url: string, impression_count: string ... 1 more field]

scala> filterDF.coalesce(1).write.format("csv").option("header","true")save("new1.csv")



Given:
* Multiple text input files
* Each file contains multiple lines
* Each line contains 3 comma separated fields: `url,impression_count,timestamp` 

Assume:
* `url` is a String 
* `impression_count` is an integer 
* `timestamp` is an integer representing unix timestamp
* `url` and `timestamp` are non unique
* All lines are formatted correctly, input validation is not required

## Instructions

***Write a program (preferably in Java or Scala) that:***

- Accept list of input files as command line arguments
- (Optional) Read input files concurrently
- Calculate `impression_count` for each URL
- Display top 100 unique URLs with highest impression count
- Display 100 oldest unique URLs (lowest `timestamp`)

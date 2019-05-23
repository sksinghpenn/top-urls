package com.lifotech.topurls

import org.scalatest.FunSuite


class BaseUrlAnalyzerTest extends FunSuite {

  def getResource(r: String): String = com.google.common.io.Resources.getResource(r).getPath


  test("getLines") {

    val fileNames: Array[String] = Array(getResource("file4.txt"), getResource("file5.txt"))
    val allLines = new UrlAnalyzer().getLines(fileNames)


    val expectedAllLines = Vector(
      "http://example.com/214-laborum-ullamco,16026,1475524196",
      "http://example.com/214-laborum-ullamco,26134,1475524197",
      "http://xyz.com/24-adipisicing-sunt,23640,1475524197",
      "http://abc.com/24-magna,21563,1475524195",
      "http://example.com/308-labore-ad,1487,1475524196",
      "http://example.com/271-minim-nisi,40385,1475524196",
      "http://example.com/171-et-elit,19388,1475524197",
      "http://example.com/171-et-elit,41403,1475524197"
    )

    assert(allLines == expectedAllLines)

  }

  test("urlCountMap") {

    val lines = Vector(
      "http://example.com/214-laborum-ullamco,16026,1475524196",
      "http://example.com/214-laborum-ullamco,26134,1475524197",
      "http://xyz.com/24-adipisicing-sunt,23640,1475524197",
      "http://example.com/271-minim-nisi,40385,1475524196",
      "http://example.com/171-et-elit,19388,1475524197",
      "http://example.com/171-et-elit,41403,1475524197"
    )

    val urlCountMap = new UrlAnalyzer().urlCountMap(lines)

    val expectedUrlCountMap = Map(
      ("http://example.com/214-laborum-ullamco" -> 42160),
      ("http://xyz.com/24-adipisicing-sunt" -> 26134),
      ("http://xyz.com/24-adipisicing-sunt" -> 23640),
      ("http://example.com/271-minim-nisi" -> 40385),
      ("http://example.com/171-et-elit" -> 60791)
    )

    assert(urlCountMap == expectedUrlCountMap)
  }

  test("UrlImpressionCountList") {
    val lines = Vector(
      "http://example.com/214-laborum-ullamco,16026,1475524196",
      "http://example.com/214-laborum-ullamco,26134,1475524197",
      "http://xyz.com/24-adipisicing-sunt,23640,1475524197"
    )

    val urlImpressionCountList = new UrlAnalyzer().UrlImpressionCountList(lines)

    val expectedUrlImpressionCountList = Vector(
      UrlImpressionCount("http://example.com/214-laborum-ullamco", 16026, 1475524196),
      UrlImpressionCount("http://example.com/214-laborum-ullamco", 26134, 1475524197),
      UrlImpressionCount("http://xyz.com/24-adipisicing-sunt", 23640, 1475524197)
    )


    assert(urlImpressionCountList == expectedUrlImpressionCountList)
  }

}

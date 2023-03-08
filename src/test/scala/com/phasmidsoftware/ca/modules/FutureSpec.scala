package com.phasmidsoftware.ca.modules

import java.net.URL
import org.scalatest.concurrent.{Futures, ScalaFutures}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.io.Source
import scala.util.{Success, Try}

class FutureSpec extends AnyFlatSpec with Matchers with Futures with ScalaFutures {

    behavior of "Future[Try[X]]"

    it should "succeed for https://www.google.com" in {
        val uyf: Future[Try[URL]] = Future(Try(new URL("https://www.google.com")))
        whenReady(uyf) { uy => uy should matchPattern { case Success(_: URL) => } }
    }

    it should "succeed for two Futures" in {
        val uyf1: Future[Try[URL]] = Future(Try(new URL("https://www.google.com")))
        val uyf2: Future[Try[URL]] = Future(Try(new URL("https://www.apple.com")))
        // NOTE that because uyf1/2 are Futures uy1/2 are Trys, we need a nested for comprehension
        val uyf: Future[Try[(URL, URL)]] = for (uy1 <- uyf1; uy2 <- uyf2) yield for (u1 <- uy1; u2 <- uy2) yield u1 -> u2
//        val z: Future[Seq[Try[URL]]] = Future.sequence(Seq(uyf1,uyf2))
        whenReady(uyf) { uUy => uUy should matchPattern { case Success((_, _)) => } }
    }

    behavior of "Using"

    it should "work" in {
        val triedList: Try[List[String]] = Using(new URL("https://google.com").openConnection().getInputStream) {
            is => Source.fromInputStream(is).getLines().toList
        }
        println(triedList)
        triedList should matchPattern { case Success(_ :: _) => }
        // NOTE normally, we shouldn't use get or head in code:
        //  but this is test code and here, we've already, checked that we've got a successful non-empty list.
        triedList.get.head.startsWith("<!doctype html>") shouldBe true
    }

    it should "get contentFromURL" in {
        val sf: Future[String] = Useful.contentFromURL("https://www.phasmidsoftware.com")
        // We do have to block here otherwise the program ends without us seeing a result.
        // This is a less satisfactory way of waiting than using whenReady(sf) -- see unit tests above.
        Await.result(sf, Duration("10 second"))
    }
    def factorial(x: Int): Int = {
        @tailrec
        def inner(r: Int, w: Int): Int = if (w > 1) inner(r * w, w - 1) else r

        inner(1, x)
    }
}

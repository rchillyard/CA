package com.phasmidsoftware.ca.modules

import com.phasmidsoftware.ca.modules.Useful.{contentFromURL, flatten, sourceFromURL}
import java.net.URL
import org.scalatest.concurrent.{Futures, ScalaFutures}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import org.scalatest.time.{Seconds, Span}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.io.Source
import scala.util.Try

class UsefulSpec extends AnyFlatSpec with should.Matchers with Futures with ScalaFutures {

    behavior of "Useful"

    it should "flatten" in {
        import scala.concurrent.ExecutionContext.Implicits.global
        val url = "https://www.phasmidsoftware.com"
        val uy = Try(new URL(url))
        val sfy: Try[Future[Source]] = uy map (u => sourceFromURL(u))
        for (s <- flatten(sfy)) yield s.getLines().mkString(" ")
    }

    it should "sourceFromURL" in {
        // WhenReady with timeout value of one second...
        val lines = whenReady(sourceFromURL(new URL("https://www.phasmidsoftware.com")), timeout(Span(1, Seconds))) {
            s => s.getLines().mkString(" ")
        }
        lines.startsWith("<!DOCTYPE html>") shouldBe true
    }

    it should "contentFromURL" in {
        val sf: Future[String] = contentFromURL("https://www.phasmidsoftware.com")
        // We do have to block here otherwise the program ends without us seeing a result.
        // This is a less satisfactory way of waiting than using whenReady(sf) -- see unit tests above.
        val s: String = Await.result(sf, Duration("10 second"))
        s.startsWith("<!DOCTYPE html>") shouldBe true
    }
}

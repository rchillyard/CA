package com.phasmidsoftware.ca.modules

import scala.util.{Failure, Success, Try}

object Parser {
    /**
     * Method parseNumber whose purpose is primarily to demonstrate a use case for Either.
     * See https://github.com/rchillyard/Number for an example of nested Either's.
     *
     * @param s a String to be parsed.
     * @return an Option of Either[Double, Int]
     */
    def parseNumber(s: String): Option[Either[Double, Int]] = Try(s.toInt) match {
        case Success(x) => Some(Right(x))
        case Failure(_) => Try(s.toDouble) match {
            case Success(x) => Some(Left(x))
            case Failure(_) => None
        }
    }

}

package com.phasmidsoftware.ca.modules

import java.net.URL
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source
import scala.util.control.{ControlThrowable, NonFatal}
import scala.util.{Failure, Success, Try}

object Useful {

    def contentFromURL(url: String): Future[String] = {
        val uy = Try(new URL(url))
        val sfy: Try[Future[Source]] = uy map (u => sourceFromURL(u))
        for (s <- flatten(sfy)) yield s.getLines().mkString(" ")
    }

    def sourceFromURL(u: URL): Future[Source] = for {
        c <- Future(u.openConnection())
        is <- Future(c.getInputStream)
    } yield Source.fromInputStream(is)

    def flatten[X](xfy: Try[Future[X]]): Future[X] =
        xfy match {
            case Success(xf) => xf
            case Failure(e) => Future.failed(e)
        }
}

object Using {
    def apply[R: Releasable, A](resource: => R)(f: R => A): Try[A] = Try {
        Using.resource(resource)(f)
    }

    /** Performs an operation using a resource, and then releases the resource,
     * even if the operation throws an exception. This method behaves similarly
     * to Java's try-with-resources.
     *
     * $suppressionBehavior
     *
     * @param resource the resource
     * @param body     the operation to perform with the resource
     * @tparam R the type of the resource
     * @tparam A the return type of the operation
     * @return the result of the operation, if neither the operation nor
     *         releasing the resource throws
     */
    private def resource[R, A](resource: R)(body: R => A)(implicit releasable: Releasable[R]): A = {

        if (resource == null) throw new NullPointerException("null resource")

        var toThrow: Throwable = null
        try {
            body(resource)
        } catch {
            case t: Throwable =>
                toThrow = t
                null.asInstanceOf[A] // compiler doesn't know `finally` will throw
        } finally {
            if (toThrow eq null) releasable.release(resource)
            else {
                try releasable.release(resource)
                catch {
                    case other: Throwable => toThrow = preferentiallySuppress(toThrow, other)
                }
                finally throw toThrow
            }
        }
    }

    private def preferentiallySuppress(primary: Throwable, secondary: Throwable): Throwable = {
        def score(t: Throwable): Int = t match {
            case _: VirtualMachineError => 4
            case _: LinkageError => 3
            case _: InterruptedException | _: ThreadDeath => 2
            case _: ControlThrowable => 0
            case e if !NonFatal(e) => 1 // in case this method gets out of sync with NonFatal
            case _ => -1
        }

        @inline def suppress(t: Throwable, suppressed: Throwable): Throwable = {
            t.addSuppressed(suppressed)
            t
        }

        if (score(secondary) > score(primary)) suppress(secondary, primary)
        else suppress(primary, secondary)
    }

}

/** A type class describing how to release a particular type of resource.
 *
 * A resource is anything which needs to be released, closed, or otherwise cleaned up
 * in some way after it is finished being used, and for which waiting for the object's
 * garbage collection to be cleaned up would be unacceptable. For example, an instance of
 * [[java.io.OutputStream]] would be considered a resource, because it is important to close
 * the stream after it is finished being used.
 *
 * An instance of `Releasable` is needed in order to automatically manage a resource
 * with [[Using `Using`]]. An implicit instance is provided for all types extending
 * [[java.lang.AutoCloseable]].
 *
 * @tparam R the type of the resource
 */
trait Releasable[-R] {
    /** Releases the specified resource. */
    def release(resource: R): Unit
}

object Releasable {
    /** An implicit `Releasable` for [[java.lang.AutoCloseable `AutoCloseable`s]]. */
    implicit object AutoCloseableIsReleasable extends Releasable[AutoCloseable] {
        def release(resource: AutoCloseable): Unit = resource.close()
    }
}

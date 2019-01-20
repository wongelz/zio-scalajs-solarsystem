import org.scalajs.dom.document
import org.scalajs.dom.html.{Canvas, Image}
import scalaz.zio.duration._
import scalaz.zio.{App, IO, Schedule}

import scala.scalajs.js.Date

object SolarSystemExample extends App {

  final case class SolarSystem(
    sun: Image,
    moon: Image,
    earth: Image
  )

  override def run(args: List[String]): IO[Nothing, Unit] =
    for {
      s <- init
      _ <- draw(s).repeat(Schedule.fixed(10.millisecond))
    } yield ()

  def init: IO[Nothing, SolarSystem] =
    for {
      sun   <- loadImage("https://mdn.mozillademos.org/files/1456/Canvas_sun.png")
      moon  <- loadImage("https://mdn.mozillademos.org/files/1443/Canvas_moon.png")
      earth <- loadImage("https://mdn.mozillademos.org/files/1429/Canvas_earth.png")
    } yield SolarSystem(sun, moon, earth)

  def draw(s: SolarSystem): IO[Nothing, Unit] = {
    for {
      c <- IO.sync { document.getElementById("canvas").asInstanceOf[Canvas].getContext("2d") }

      _ <- IO.sync { c.globalCompositeOperation = "destination-over" }
      _ <- IO.sync { c.clearRect(0, 0, 300, 300) }

      _ <- IO.sync { c.fillStyle = "rgba(0, 0, 0, 0.4)"}
      _ <- IO.sync { c.strokeStyle = "rgba(0, 153, 255, 0.4)"}
      _ <- IO.sync { c.save() }
      _ <- IO.sync { c.translate(150, 150) }

      // earth
      t <- IO.sync { new Date() }
      _ <- IO.sync { c.rotate(((2 * Math.PI) / 60) * t.getSeconds() + ((2 * Math.PI) / 60000) * t.getMilliseconds()) }
      _ <- IO.sync { c.translate(105, 0) }
      _ <- IO.sync { c.fillRect(0, -12, 40, 24) } // shadow
      _ <- IO.sync { c.drawImage(s.earth, -12, -12) }

      // moon
      _ <- IO.sync { c.save() }
      _ <- IO.sync { c.rotate(((2 * Math.PI) / 6) * t.getSeconds() + ((2 * Math.PI) / 6000) * t.getMilliseconds())}
      _ <- IO.sync { c.translate(0, 28.5) }
      _ <- IO.sync { c.drawImage(s.moon, -3.5, -3.5) }
      _ <- IO.sync { c.restore() }

      _ <- IO.sync { c.restore() }

      _ <- IO.sync { c.beginPath() }
      _ <- IO.sync { c.arc(150, 150, 105, 0, Math.PI * 2, false) } // earth orbit
      _ <- IO.sync { c.stroke() }

      _ <- IO.sync { c.drawImage(s.sun, 0, 0, 300, 300) }
    } yield ()
  }


  private def loadImage(src: String) =
    IO.sync {
      val image = document.createElement("img").asInstanceOf[Image]
      image.src = src
      image
    }
}

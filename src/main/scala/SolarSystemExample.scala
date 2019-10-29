import org.scalajs.dom.document
import org.scalajs.dom.html.{Canvas, Image}
import zio.duration._
import zio.{App, UIO, ZSchedule}

import scala.scalajs.js.Date

object SolarSystemExample extends App {

  final case class SolarSystem(
    sun: Image,
    moon: Image,
    earth: Image
  )

  override def run(args: List[String]) =
    for {
      s <- init
      _ <- draw(s).repeat(ZSchedule.fixed(10.millisecond))
    } yield 0

  def init: UIO[SolarSystem] =
    for {
      sun   <- loadImage("https://mdn.mozillademos.org/files/1456/Canvas_sun.png")
      moon  <- loadImage("https://mdn.mozillademos.org/files/1443/Canvas_moon.png")
      earth <- loadImage("https://mdn.mozillademos.org/files/1429/Canvas_earth.png")
    } yield SolarSystem(sun, moon, earth)

  def draw(s: SolarSystem): UIO[Unit] = {
    for {
      c <- UIO.effectTotal { document.getElementById("canvas").asInstanceOf[Canvas].getContext("2d") }

      _ <- UIO.effectTotal { c.globalCompositeOperation = "destination-over" }
      _ <- UIO.effectTotal { c.clearRect(0, 0, 300, 300) }

      _ <- UIO.effectTotal { c.fillStyle = "rgba(0, 0, 0, 0.4)"}
      _ <- UIO.effectTotal { c.strokeStyle = "rgba(0, 153, 255, 0.4)"}
      _ <- UIO.effectTotal { c.save() }
      _ <- UIO.effectTotal { c.translate(150, 150) }

      // earth
      t <- UIO.effectTotal { new Date() }
      _ <- UIO.effectTotal { c.rotate(((2 * Math.PI) / 60) * t.getSeconds() + ((2 * Math.PI) / 60000) * t.getMilliseconds()) }
      _ <- UIO.effectTotal { c.translate(105, 0) }
      _ <- UIO.effectTotal { c.fillRect(0, -12, 40, 24) } // shadow
      _ <- UIO.effectTotal { c.drawImage(s.earth, -12, -12) }

      // moon
      _ <- UIO.effectTotal { c.save() }
      _ <- UIO.effectTotal { c.rotate(((2 * Math.PI) / 6) * t.getSeconds() + ((2 * Math.PI) / 6000) * t.getMilliseconds())}
      _ <- UIO.effectTotal { c.translate(0, 28.5) }
      _ <- UIO.effectTotal { c.drawImage(s.moon, -3.5, -3.5) }
      _ <- UIO.effectTotal { c.restore() }

      _ <- UIO.effectTotal { c.restore() }

      _ <- UIO.effectTotal { c.beginPath() }
      _ <- UIO.effectTotal { c.arc(150, 150, 105, 0, Math.PI * 2, false) } // earth orbit
      _ <- UIO.effectTotal { c.stroke() }

      _ <- UIO.effectTotal { c.drawImage(s.sun, 0, 0, 300, 300) }
    } yield ()
  }


  private def loadImage(src: String) =
    UIO.effectTotal {
      val image = document.createElement("img").asInstanceOf[Image]
      image.src = src
      image
    }
}

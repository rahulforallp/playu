package controllers

import javax.inject._

import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString
import play.api.http.HttpEntity
import play.api.mvc._


@Singleton
class HomeController @Inject()(webJarAssets: WebJarAssets) extends Controller {

  def index = {
    Action {
      Ok(views.html.index("Your new application is ready.", webJarAssets))
    }
  }

  def streaming = {
    Action {
      val file = new java.io.File("conf/vid2.mp4")
      val path: java.nio.file.Path = file.toPath
      val source: Source[ByteString, _] = FileIO.fromPath(path)

      Result(
        header = ResponseHeader(200, Map.empty),
        body = HttpEntity.Streamed(source, Some(file.length), Some("application/mp4"))
      )

      /*Ok.sendFile(
      content = new File("conf/vid2.mp4"),
      inline = true
    )*/
    }
  }
}

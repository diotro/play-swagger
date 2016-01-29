package minimal.api.yaml

import play.api.mvc.{Action, Controller, Results}
import play.api.http.Writeable
import Results.Status
import de.zalando.play.controllers.{PlayBodyParsing, ParsingError}
import PlayBodyParsing._
import scala.util._




trait DashboardBase extends Controller with PlayBodyParsing {
    private type indexActionRequestType       = (Unit)
    private type indexActionType              = indexActionRequestType => Try[Any]

    private val errorToStatusindex: PartialFunction[Throwable, Status] = PartialFunction.empty[Throwable, Status]

    def indexAction = (f: indexActionType) => Action {        val indexResponseMimeType    = "application/json"

        val possibleWriters = Map(
                200 -> anyToWritable[Null]
        )        
            val result = processValidindexRequest(f)()                
            result
    }

    private def processValidindexRequest[T <: Any](f: indexActionType)(request: indexActionRequestType, writers: Map[Int, String => Writeable[T]], mimeType: String) = {
        val callerResult = f(request)
        val status = callerResult match {
            case Failure(error) => (errorToStatusindex orElse defaultErrorMapping)(error)
            case Success((code: Int, result: T @ unchecked)) =>
                writers.get(code).map { writer =>
                    implicit val indexWritableJson = writer(mimeType)
                    Status(code)(result)
                }.getOrElse {
                    implicit val errorWriter = anyToWritable[IllegalStateException](mimeType)
                    Status(500)(new IllegalStateException(s"Response code was not defined in specification: $code"))
                }
        }
        status
    }
}

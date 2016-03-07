package instagram.api.yaml

import de.zalando.play.controllers._
import org.scalacheck._
import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._
import org.scalacheck.Test._
import org.specs2.mutable._
import play.api.test.Helpers._
import play.api.test._
import play.api.mvc.{QueryStringBindable, PathBindable}
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import java.net.URLEncoder
import play.api.http.Writeable

import play.api.test.Helpers.{status => requestStatusCode_}
import play.api.test.Helpers.{contentAsString => requestContentAsString_}
import play.api.test.Helpers.{contentType => requestContentType_}

import Generators._

    @RunWith(classOf[JUnitRunner])
    class InstagramApiYamlSpec extends Specification {
        def toPath[T](value: T)(implicit binder: PathBindable[T]): String = Option(binder.unbind("", value)).getOrElse("")
        def toQuery[T](key: String, value: T)(implicit binder: QueryStringBindable[T]): String = Option(binder.unbind(key, value)).getOrElse("")
        def toHeader[T](value: T)(implicit binder: PathBindable[T]): String = Option(binder.unbind("", value)).getOrElse("")

      def checkResult(props: Prop) =
        Test.check(Test.Parameters.default, props).status match {
          case Failed(_, labels) => failure(labels.mkString("\n"))
          case Proved(_) | Exhausted | Passed => success
          case PropException(_, e, labels) =>
            val error = if (labels.isEmpty) e.getLocalizedMessage() else labels.mkString("\n")
            failure(error)
        }

      private def parserConstructor(mimeType: String) = PlayBodyParsing.jacksonMapper(mimeType)

      def parseResponseContent[T](content: String, mimeType: Option[String], expectedType: Class[T]) =
        parserConstructor(mimeType.getOrElse("application/json")).readValue(content, expectedType)



    "GET /v1/users/{user-id}" should {
        def testInvalidInput(user_id: Double) = {


            val url = s"""/v1/users/${toPath(user_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idGetValidator(user_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(user_id: Double) = {            
            val url = s"""/v1/users/${toPath(user_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idGetValidator(user_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    user_id <- DoubleGenerator
                } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idGetValidator(user_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                user_id <- DoubleGenerator
            } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idGetValidator(user_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/users/{user-id}/followed-by" should {
        def testInvalidInput(user_id: Double) = {


            val url = s"""/v1/users/${toPath(user_id)}/followed-by"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idFollowed_byGetValidator(user_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(user_id: Double) = {            
            val url = s"""/v1/users/${toPath(user_id)}/followed-by"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idFollowed_byGetValidator(user_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    user_id <- DoubleGenerator
                } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idFollowed_byGetValidator(user_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                user_id <- DoubleGenerator
            } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idFollowed_byGetValidator(user_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/media/{media-id}/likes" should {
        def testInvalidInput(media_id: Int) = {


            val url = s"""/v1/media/${toPath(media_id)}/likes"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idLikesGetValidator(media_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(media_id: Int) = {            
            val url = s"""/v1/media/${toPath(media_id)}/likes"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idLikesGetValidator(media_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    media_id <- IntGenerator
                } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idLikesGetValidator(media_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                media_id <- IntGenerator
            } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idLikesGetValidator(media_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/locations/search" should {
        def testInvalidInput(input: (MediaId, MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude)) = {


                val (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) = input
            
            val url = s"""/v1/locations/search?${toQuery("foursquare_v2_id", foursquare_v2_id)}&${toQuery("facebook_places_id", facebook_places_id)}&${toQuery("distance", distance)}&${toQuery("lat", lat)}&${toQuery("foursquare_id", foursquare_id)}&${toQuery("lng", lng)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaId, MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude)) = {
                val (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) = input
                        
            val url = s"""/v1/locations/search?${toQuery("foursquare_v2_id", foursquare_v2_id)}&${toQuery("facebook_places_id", facebook_places_id)}&${toQuery("distance", distance)}&${toQuery("lat", lat)}&${toQuery("foursquare_id", foursquare_id)}&${toQuery("lng", lng)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        foursquare_v2_id <- MediaIdGenerator
                        facebook_places_id <- MediaIdGenerator
                        distance <- MediaIdGenerator
                        lat <- LocationLatitudeGenerator
                        foursquare_id <- MediaIdGenerator
                        lng <- LocationLatitudeGenerator
                    
                } yield (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng)
            val inputs = genInputs suchThat { case (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) =>
                new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    foursquare_v2_id <- MediaIdGenerator
                    facebook_places_id <- MediaIdGenerator
                    distance <- MediaIdGenerator
                    lat <- LocationLatitudeGenerator
                    foursquare_id <- MediaIdGenerator
                    lng <- LocationLatitudeGenerator
                
            } yield (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng)
            val inputs = genInputs suchThat { case (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) =>
                new LocationsSearchGetValidator(foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "DELETE /v1/media/{media-id}/comments" should {
        def testInvalidInput(media_id: Int) = {


            val url = s"""/v1/media/${toPath(media_id)}/comments"""
            val headers = Seq()

            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idCommentsDeleteValidator(media_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(media_id: Int) = {            
            val url = s"""/v1/media/${toPath(media_id)}/comments"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idCommentsDeleteValidator(media_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    media_id <- IntGenerator
                } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idCommentsDeleteValidator(media_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                media_id <- IntGenerator
            } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idCommentsDeleteValidator(media_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/users/self/media/liked" should {
        def testInvalidInput(input: (MediaId, MediaId)) = {


                val (count, max_like_id) = input
            
            val url = s"""/v1/users/self/media/liked?${toQuery("count", count)}&${toQuery("max_like_id", max_like_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSelfMediaLikedGetValidator(count, max_like_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaId, MediaId)) = {
                val (count, max_like_id) = input
                        
            val url = s"""/v1/users/self/media/liked?${toQuery("count", count)}&${toQuery("max_like_id", max_like_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSelfMediaLikedGetValidator(count, max_like_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        count <- MediaIdGenerator
                        max_like_id <- MediaIdGenerator
                    
                } yield (count, max_like_id)
            val inputs = genInputs suchThat { case (count, max_like_id) =>
                new UsersSelfMediaLikedGetValidator(count, max_like_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    count <- MediaIdGenerator
                    max_like_id <- MediaIdGenerator
                
            } yield (count, max_like_id)
            val inputs = genInputs suchThat { case (count, max_like_id) =>
                new UsersSelfMediaLikedGetValidator(count, max_like_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/tags/search" should {
        def testInvalidInput(q: MediaFilter) = {


            val url = s"""/v1/tags/search?${toQuery("q", q)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TagsSearchGetValidator(q).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(q: MediaFilter) = {            
            val url = s"""/v1/tags/search?${toQuery("q", q)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TagsSearchGetValidator(q).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    q <- MediaFilterGenerator
                } yield q
            val inputs = genInputs suchThat { q =>
                new TagsSearchGetValidator(q).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                q <- MediaFilterGenerator
            } yield q
            val inputs = genInputs suchThat { q =>
                new TagsSearchGetValidator(q).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/media/{media-id}/comments" should {
        def testInvalidInput(media_id: Int) = {


            val url = s"""/v1/media/${toPath(media_id)}/comments"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idCommentsGetValidator(media_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(media_id: Int) = {            
            val url = s"""/v1/media/${toPath(media_id)}/comments"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idCommentsGetValidator(media_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    media_id <- IntGenerator
                } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idCommentsGetValidator(media_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                media_id <- IntGenerator
            } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idCommentsGetValidator(media_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "DELETE /v1/media/{media-id}/likes" should {
        def testInvalidInput(media_id: Int) = {


            val url = s"""/v1/media/${toPath(media_id)}/likes"""
            val headers = Seq()

            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idLikesDeleteValidator(media_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(media_id: Int) = {            
            val url = s"""/v1/media/${toPath(media_id)}/likes"""
            val headers = Seq()
            val path = route(FakeRequest(DELETE, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idLikesDeleteValidator(media_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    media_id <- IntGenerator
                } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idLikesDeleteValidator(media_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                media_id <- IntGenerator
            } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idLikesDeleteValidator(media_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/media/{media-id}" should {
        def testInvalidInput(media_id: Int) = {


            val url = s"""/v1/media/${toPath(media_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idGetValidator(media_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(media_id: Int) = {            
            val url = s"""/v1/media/${toPath(media_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idGetValidator(media_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    media_id <- IntGenerator
                } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idGetValidator(media_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                media_id <- IntGenerator
            } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idGetValidator(media_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/media/{shortcode}" should {
        def testInvalidInput(shortcode: String) = {


            val url = s"""/v1/media/${toPath(shortcode)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaShortcodeGetValidator(shortcode).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(shortcode: String) = {            
            val url = s"""/v1/media/${toPath(shortcode)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaShortcodeGetValidator(shortcode).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    shortcode <- StringGenerator
                } yield shortcode
            val inputs = genInputs suchThat { shortcode =>
                new MediaShortcodeGetValidator(shortcode).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                shortcode <- StringGenerator
            } yield shortcode
            val inputs = genInputs suchThat { shortcode =>
                new MediaShortcodeGetValidator(shortcode).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/users/search" should {
        def testInvalidInput(input: (String, MediaFilter)) = {


                val (q, count) = input
            
            val url = s"""/v1/users/search?${toQuery("q", q)}&${toQuery("count", count)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSearchGetValidator(q, count).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (String, MediaFilter)) = {
                val (q, count) = input
                        
            val url = s"""/v1/users/search?${toQuery("q", q)}&${toQuery("count", count)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSearchGetValidator(q, count).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        q <- StringGenerator
                        count <- MediaFilterGenerator
                    
                } yield (q, count)
            val inputs = genInputs suchThat { case (q, count) =>
                new UsersSearchGetValidator(q, count).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    q <- StringGenerator
                    count <- MediaFilterGenerator
                
            } yield (q, count)
            val inputs = genInputs suchThat { case (q, count) =>
                new UsersSearchGetValidator(q, count).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "POST /v1/media/{media-id}/comments" should {
        def testInvalidInput(input: (Int, LocationLatitude)) = {


                val (media_id, tEXT) = input
            
            val url = s"""/v1/media/${toPath(media_id)}/comments"""
            val headers = Seq()
                val parsed_TEXT = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(TEXT)

            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_TEXT)).get
            val errors = new MediaMedia_idCommentsPostValidator(media_id, tEXT).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_TEXT + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Int, LocationLatitude)) = {
                val (media_id, tEXT) = input
                        
                val parsed_TEXT = parserConstructor("application/json").writeValueAsString(TEXT)
            
            val url = s"""/v1/media/${toPath(media_id)}/comments"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_TEXT)).get
            val errors = new MediaMedia_idCommentsPostValidator(media_id, tEXT).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_TEXT + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        media_id <- IntGenerator
                        tEXT <- LocationLatitudeGenerator
                    
                } yield (media_id, tEXT)
            val inputs = genInputs suchThat { case (media_id, tEXT) =>
                new MediaMedia_idCommentsPostValidator(media_id, tEXT).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    media_id <- IntGenerator
                    tEXT <- LocationLatitudeGenerator
                
            } yield (media_id, tEXT)
            val inputs = genInputs suchThat { case (media_id, tEXT) =>
                new MediaMedia_idCommentsPostValidator(media_id, tEXT).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "POST /v1/media/{media-id}/likes" should {
        def testInvalidInput(media_id: Int) = {


            val url = s"""/v1/media/${toPath(media_id)}/likes"""
            val headers = Seq()

            val path = route(FakeRequest(POST, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idLikesPostValidator(media_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(media_id: Int) = {            
            val url = s"""/v1/media/${toPath(media_id)}/likes"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*)).get
            val errors = new MediaMedia_idLikesPostValidator(media_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    media_id <- IntGenerator
                } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idLikesPostValidator(media_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                media_id <- IntGenerator
            } yield media_id
            val inputs = genInputs suchThat { media_id =>
                new MediaMedia_idLikesPostValidator(media_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "POST /v1/users/{user-id}/relationship" should {
        def testInvalidInput(input: (Double, MediaFilter)) = {


                val (user_id, action) = input
            
            val url = s"""/v1/users/${toPath(user_id)}/relationship"""
            val headers = Seq()
                val parsed_action = PlayBodyParsing.jacksonMapper("application/json").writeValueAsString(action)

            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_action)).get
            val errors = new UsersUser_idRelationshipPostValidator(user_id, action).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" + "and body [" + parsed_action + "]") |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Double, MediaFilter)) = {
                val (user_id, action) = input
                        
                val parsed_action = parserConstructor("application/json").writeValueAsString(action)
            
            val url = s"""/v1/users/${toPath(user_id)}/relationship"""
            val headers = Seq()
            val path = route(FakeRequest(POST, url).withHeaders(headers:_*).withBody(parsed_action)).get
            val errors = new UsersUser_idRelationshipPostValidator(user_id, action).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" + "and body [" + parsed_action + "]") |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        user_id <- DoubleGenerator
                        action <- MediaFilterGenerator
                    
                } yield (user_id, action)
            val inputs = genInputs suchThat { case (user_id, action) =>
                new UsersUser_idRelationshipPostValidator(user_id, action).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    user_id <- DoubleGenerator
                    action <- MediaFilterGenerator
                
            } yield (user_id, action)
            val inputs = genInputs suchThat { case (user_id, action) =>
                new UsersUser_idRelationshipPostValidator(user_id, action).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/tags/{tag-name}" should {
        def testInvalidInput(tag_name: String) = {


            val url = s"""/v1/tags/${toPath(tag_name)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TagsTag_nameGetValidator(tag_name).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(tag_name: String) = {            
            val url = s"""/v1/tags/${toPath(tag_name)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TagsTag_nameGetValidator(tag_name).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    tag_name <- StringGenerator
                } yield tag_name
            val inputs = genInputs suchThat { tag_name =>
                new TagsTag_nameGetValidator(tag_name).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                tag_name <- StringGenerator
            } yield tag_name
            val inputs = genInputs suchThat { tag_name =>
                new TagsTag_nameGetValidator(tag_name).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/locations/{location-id}" should {
        def testInvalidInput(location_id: Int) = {


            val url = s"""/v1/locations/${toPath(location_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new LocationsLocation_idGetValidator(location_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(location_id: Int) = {            
            val url = s"""/v1/locations/${toPath(location_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new LocationsLocation_idGetValidator(location_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    location_id <- IntGenerator
                } yield location_id
            val inputs = genInputs suchThat { location_id =>
                new LocationsLocation_idGetValidator(location_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                location_id <- IntGenerator
            } yield location_id
            val inputs = genInputs suchThat { location_id =>
                new LocationsLocation_idGetValidator(location_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/locations/{location-id}/media/recent" should {
        def testInvalidInput(input: (Int, MediaId, MediaId, MediaFilter, MediaFilter)) = {


                val (location_id, max_timestamp, min_timestamp, min_id, max_id) = input
            
            val url = s"""/v1/locations/${toPath(location_id)}/media/recent?${toQuery("max_timestamp", max_timestamp)}&${toQuery("min_timestamp", min_timestamp)}&${toQuery("min_id", min_id)}&${toQuery("max_id", max_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new LocationsLocation_idMediaRecentGetValidator(location_id, max_timestamp, min_timestamp, min_id, max_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Int, MediaId, MediaId, MediaFilter, MediaFilter)) = {
                val (location_id, max_timestamp, min_timestamp, min_id, max_id) = input
                        
            val url = s"""/v1/locations/${toPath(location_id)}/media/recent?${toQuery("max_timestamp", max_timestamp)}&${toQuery("min_timestamp", min_timestamp)}&${toQuery("min_id", min_id)}&${toQuery("max_id", max_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new LocationsLocation_idMediaRecentGetValidator(location_id, max_timestamp, min_timestamp, min_id, max_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        location_id <- IntGenerator
                        max_timestamp <- MediaIdGenerator
                        min_timestamp <- MediaIdGenerator
                        min_id <- MediaFilterGenerator
                        max_id <- MediaFilterGenerator
                    
                } yield (location_id, max_timestamp, min_timestamp, min_id, max_id)
            val inputs = genInputs suchThat { case (location_id, max_timestamp, min_timestamp, min_id, max_id) =>
                new LocationsLocation_idMediaRecentGetValidator(location_id, max_timestamp, min_timestamp, min_id, max_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    location_id <- IntGenerator
                    max_timestamp <- MediaIdGenerator
                    min_timestamp <- MediaIdGenerator
                    min_id <- MediaFilterGenerator
                    max_id <- MediaFilterGenerator
                
            } yield (location_id, max_timestamp, min_timestamp, min_id, max_id)
            val inputs = genInputs suchThat { case (location_id, max_timestamp, min_timestamp, min_id, max_id) =>
                new LocationsLocation_idMediaRecentGetValidator(location_id, max_timestamp, min_timestamp, min_id, max_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/media/search" should {
        def testInvalidInput(input: (MediaId, Int, LocationLatitude, MediaId, LocationLatitude)) = {


                val (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) = input
            
            val url = s"""/v1/media/search?${toQuery("MAX_TIMESTAMP", MAX_TIMESTAMP)}&${toQuery("DISTANCE", DISTANCE)}&${toQuery("LNG", LNG)}&${toQuery("MIN_TIMESTAMP", MIN_TIMESTAMP)}&${toQuery("LAT", LAT)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaId, Int, LocationLatitude, MediaId, LocationLatitude)) = {
                val (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) = input
                        
            val url = s"""/v1/media/search?${toQuery("MAX_TIMESTAMP", MAX_TIMESTAMP)}&${toQuery("DISTANCE", DISTANCE)}&${toQuery("LNG", LNG)}&${toQuery("MIN_TIMESTAMP", MIN_TIMESTAMP)}&${toQuery("LAT", LAT)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        mAX_TIMESTAMP <- MediaIdGenerator
                        dISTANCE <- IntGenerator
                        lNG <- LocationLatitudeGenerator
                        mIN_TIMESTAMP <- MediaIdGenerator
                        lAT <- LocationLatitudeGenerator
                    
                } yield (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT)
            val inputs = genInputs suchThat { case (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) =>
                new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    mAX_TIMESTAMP <- MediaIdGenerator
                    dISTANCE <- IntGenerator
                    lNG <- LocationLatitudeGenerator
                    mIN_TIMESTAMP <- MediaIdGenerator
                    lAT <- LocationLatitudeGenerator
                
            } yield (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT)
            val inputs = genInputs suchThat { case (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) =>
                new MediaSearchGetValidator(mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/tags/{tag-name}/media/recent" should {
        def testInvalidInput(tag_name: String) = {


            val url = s"""/v1/tags/${toPath(tag_name)}/media/recent"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TagsTag_nameMediaRecentGetValidator(tag_name).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(tag_name: String) = {            
            val url = s"""/v1/tags/${toPath(tag_name)}/media/recent"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new TagsTag_nameMediaRecentGetValidator(tag_name).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    tag_name <- StringGenerator
                } yield tag_name
            val inputs = genInputs suchThat { tag_name =>
                new TagsTag_nameMediaRecentGetValidator(tag_name).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                tag_name <- StringGenerator
            } yield tag_name
            val inputs = genInputs suchThat { tag_name =>
                new TagsTag_nameMediaRecentGetValidator(tag_name).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/users/{user-id}/follows" should {
        def testInvalidInput(user_id: Double) = {


            val url = s"""/v1/users/${toPath(user_id)}/follows"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idFollowsGetValidator(user_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(user_id: Double) = {            
            val url = s"""/v1/users/${toPath(user_id)}/follows"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idFollowsGetValidator(user_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                    user_id <- DoubleGenerator
                } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idFollowsGetValidator(user_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                user_id <- DoubleGenerator
            } yield user_id
            val inputs = genInputs suchThat { user_id =>
                new UsersUser_idFollowsGetValidator(user_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/users/{user-id}/media/recent" should {
        def testInvalidInput(input: (Double, MediaId, MediaFilter, MediaId, MediaFilter, MediaId)) = {


                val (user_id, max_timestamp, min_id, min_timestamp, max_id, count) = input
            
            val url = s"""/v1/users/${toPath(user_id)}/media/recent?${toQuery("max_timestamp", max_timestamp)}&${toQuery("min_id", min_id)}&${toQuery("min_timestamp", min_timestamp)}&${toQuery("max_id", max_id)}&${toQuery("count", count)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idMediaRecentGetValidator(user_id, max_timestamp, min_id, min_timestamp, max_id, count).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Double, MediaId, MediaFilter, MediaId, MediaFilter, MediaId)) = {
                val (user_id, max_timestamp, min_id, min_timestamp, max_id, count) = input
                        
            val url = s"""/v1/users/${toPath(user_id)}/media/recent?${toQuery("max_timestamp", max_timestamp)}&${toQuery("min_id", min_id)}&${toQuery("min_timestamp", min_timestamp)}&${toQuery("max_id", max_id)}&${toQuery("count", count)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersUser_idMediaRecentGetValidator(user_id, max_timestamp, min_id, min_timestamp, max_id, count).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        user_id <- DoubleGenerator
                        max_timestamp <- MediaIdGenerator
                        min_id <- MediaFilterGenerator
                        min_timestamp <- MediaIdGenerator
                        max_id <- MediaFilterGenerator
                        count <- MediaIdGenerator
                    
                } yield (user_id, max_timestamp, min_id, min_timestamp, max_id, count)
            val inputs = genInputs suchThat { case (user_id, max_timestamp, min_id, min_timestamp, max_id, count) =>
                new UsersUser_idMediaRecentGetValidator(user_id, max_timestamp, min_id, min_timestamp, max_id, count).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    user_id <- DoubleGenerator
                    max_timestamp <- MediaIdGenerator
                    min_id <- MediaFilterGenerator
                    min_timestamp <- MediaIdGenerator
                    max_id <- MediaFilterGenerator
                    count <- MediaIdGenerator
                
            } yield (user_id, max_timestamp, min_id, min_timestamp, max_id, count)
            val inputs = genInputs suchThat { case (user_id, max_timestamp, min_id, min_timestamp, max_id, count) =>
                new UsersUser_idMediaRecentGetValidator(user_id, max_timestamp, min_id, min_timestamp, max_id, count).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/users/self/feed" should {
        def testInvalidInput(input: (MediaId, MediaId, MediaId)) = {


                val (count, max_id, min_id) = input
            
            val url = s"""/v1/users/self/feed?${toQuery("count", count)}&${toQuery("max_id", max_id)}&${toQuery("min_id", min_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSelfFeedGetValidator(count, max_id, min_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (MediaId, MediaId, MediaId)) = {
                val (count, max_id, min_id) = input
                        
            val url = s"""/v1/users/self/feed?${toQuery("count", count)}&${toQuery("max_id", max_id)}&${toQuery("min_id", min_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new UsersSelfFeedGetValidator(count, max_id, min_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        count <- MediaIdGenerator
                        max_id <- MediaIdGenerator
                        min_id <- MediaIdGenerator
                    
                } yield (count, max_id, min_id)
            val inputs = genInputs suchThat { case (count, max_id, min_id) =>
                new UsersSelfFeedGetValidator(count, max_id, min_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    count <- MediaIdGenerator
                    max_id <- MediaIdGenerator
                    min_id <- MediaIdGenerator
                
            } yield (count, max_id, min_id)
            val inputs = genInputs suchThat { case (count, max_id, min_id) =>
                new UsersSelfFeedGetValidator(count, max_id, min_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }


    "GET /v1/geographies/{geo-id}/media/recent" should {
        def testInvalidInput(input: (Int, MediaId, MediaId)) = {


                val (geo_id, count, min_id) = input
            
            val url = s"""/v1/geographies/${toPath(geo_id)}/media/recent?${toQuery("count", count)}&${toQuery("min_id", min_id)}"""
            val headers = Seq()

            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new GeographiesGeo_idMediaRecentGetValidator(geo_id, count, min_id).errors

            lazy val validations = errors flatMap { _.messages } map { m => contentAsString(path).contains(m) ?= true }

            ("given an URL: [" + url + "]" ) |: all(
                requestStatusCode_(path) ?= BAD_REQUEST ,
                requestContentType_(path) ?= Some("application/json"),
                errors.nonEmpty ?= true,
                all(validations:_*)
            )
        }
        def testValidInput(input: (Int, MediaId, MediaId)) = {
                val (geo_id, count, min_id) = input
                        
            val url = s"""/v1/geographies/${toPath(geo_id)}/media/recent?${toQuery("count", count)}&${toQuery("min_id", min_id)}"""
            val headers = Seq()
            val path = route(FakeRequest(GET, url).withHeaders(headers:_*)).get
            val errors = new GeographiesGeo_idMediaRecentGetValidator(geo_id, count, min_id).errors
            val possibleResponseTypes: Map[Int,Class[Any]] = Map.empty[Int,Class[Any]]
            val expectedCode = requestStatusCode_(path)
            val expectedResponseType = possibleResponseTypes(expectedCode)

            val parsedApiResponse = scala.util.Try {
                parseResponseContent(requestContentAsString_(path), requestContentType_(path), expectedResponseType)
            }
            ("given an URL: [" + url + "]" ) |: all(
                parsedApiResponse.isSuccess ?= true,
                requestContentType_(path) ?= Some("application/json"),
                errors.isEmpty ?= true
            )
        }
        "discard invalid data" in new WithApplication {
            val genInputs = for {
                        geo_id <- IntGenerator
                        count <- MediaIdGenerator
                        min_id <- MediaIdGenerator
                    
                } yield (geo_id, count, min_id)
            val inputs = genInputs suchThat { case (geo_id, count, min_id) =>
                new GeographiesGeo_idMediaRecentGetValidator(geo_id, count, min_id).errors.nonEmpty
            }
            val props = forAll(inputs) { i => testInvalidInput(i) }
            checkResult(props)
        }
        "do something with valid data" in new WithApplication {
            val genInputs = for {
                    geo_id <- IntGenerator
                    count <- MediaIdGenerator
                    min_id <- MediaIdGenerator
                
            } yield (geo_id, count, min_id)
            val inputs = genInputs suchThat { case (geo_id, count, min_id) =>
                new GeographiesGeo_idMediaRecentGetValidator(geo_id, count, min_id).errors.isEmpty
            }
            val props = forAll(inputs) { i => testValidInput(i) }
            checkResult(props)
        }

    }

}
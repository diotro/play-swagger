package full.petstore.api.yaml

import play.api.mvc._
import Security.AuthenticatedBuilder
import de.zalando.play.controllers.PlayBodyParsing
import de.zalando.play.controllers.ArrayWrapper
import org.joda.time.DateTime


trait FullPetstoreApiYamlSecurity extends SecurityExtractors {
    
    object findPetsByTagsSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object updatePetSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object addPetSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object getPetByIdSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(api_key_Extractor _, petstore_auth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object updatePetWithFormSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object deletePetSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
    object findPetsByStatusSecureAction extends AuthenticatedBuilder(
        req => {
            val secureChecks = Seq(petstore_auth_Extractor _)
            val individualChecks: Seq[Option[_]] = secureChecks.map(_.apply(req))
            individualChecks.find(_.isEmpty).getOrElse(Option(individualChecks.flatten))
        }, unauthorizedContent)
    
}

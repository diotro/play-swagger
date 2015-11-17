package de.zalando.swagger

import java.net.URI

import de.zalando.apifirst.Application.StrictModel
import de.zalando.apifirst.Domain.Type
import de.zalando.apifirst.new_naming.Reference
import de.zalando.swagger.strictModel.SwaggerModel

/**
 * @author  slasch
 * @since   20.10.2015.
 */
trait ParameterNaming {
  type Types = Seq[Type]
  type NamedType = (Reference, Type)
  type NamedTypes = Seq[NamedType]
}

object ModelConverter extends ParameterNaming {

  def fromModel(base: URI, model: SwaggerModel, keyPrefix: String = "x-api-first") = {
    val converter = new TypeConverter(base, model, keyPrefix)
    val typeDefs = converter.convert
    val discriminators = converter.discriminators.toMap
    val inlineParameters = new ParametersConverter(base, model, keyPrefix, typeDefs).parameters // TODO add explicitly defined parameters here
    val apiCalls = new PathsConverter(base, model, keyPrefix, inlineParameters).convert
    StrictModel(apiCalls, typeDefs.toMap, inlineParameters, discriminators)
  }

}
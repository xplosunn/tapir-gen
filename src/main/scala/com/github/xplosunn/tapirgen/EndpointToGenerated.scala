package com.github.xplosunn.tapirgen

import EndpointToGenerated._
import TypeDeclaration.TypeName
import tapir.EndpointInput.FixedMethod
import tapir._
import tapir.internal._

class EndpointToGenerated[L: Language](packageName: String, httpCaller: HttpCaller[L]) {

  def generate[O](apiName: String, endpoint: Endpoint[_, _, O, _]): String = {
    val inputs = endpoint.input.asVectorOfBasicInputs(includeAuth = false)
    val outcome = inputsToOutcomeMap(inputs)
    Language[L].generateCode(packageName, apiName, Set.empty, outcome, httpCaller)
  }

  private def inputsToOutcomeMap(inputs: Seq[EndpointInput.Basic[_]]): Set[OutcomeOfPathElement] = {
    val call: Set[OutcomeOfPathElement] = inputs.collect {
      case FixedMethod(method) =>
        Set(HttpCall(method.m, None, TypeName("String"), TypeName("String")))
    }.flatten.toSet

    inputs.foldRight(call) {
      case (EndpointInput.FixedPath(s), acc) =>
        Set(Function(s, acc))
      case (EndpointInput.PathCapture(_, name, _), acc) =>
        Set(Parameter(name.get, "String", acc))
      case (EndpointInput.PathsCapture(info), acc) =>
        Set(Function(info.description.get, acc))
      case (_, acc) =>
        acc
    }
  }
}

object EndpointToGenerated {
  sealed trait OutcomeOfPathElement
  case class Parameter(name: String, paramTypeName: String, impl: Set[OutcomeOfPathElement]) extends OutcomeOfPathElement
  case class Function(name: String, impl: Set[OutcomeOfPathElement]) extends OutcomeOfPathElement
  case class HttpCall[Payload, Failure, Response](
                                                 method: String,
                                                 payloadType: Option[TypeName[Payload]],
                                                 error: TypeName[Failure],
                                                 response: TypeName[Response]
                                               ) extends OutcomeOfPathElement
}

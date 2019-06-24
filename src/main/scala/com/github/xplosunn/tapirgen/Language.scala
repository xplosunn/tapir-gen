package com.github.xplosunn.tapirgen

import com.github.xplosunn.tapirgen.EndpointToGenerated.OutcomeOfPathElement

trait Language[L] {
  def generateCode(packageName: String,
                    apiName: String,
                   typeDescriptions: Set[TypeDeclaration[_]],
                   map: Set[OutcomeOfPathElement],
                   httpCaller: HttpCaller[L]): String
}

case object Language {
  def apply[T](implicit language: Language[T]) = language
}
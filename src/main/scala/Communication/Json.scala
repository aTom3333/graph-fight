package Communication

import java.lang.annotation.Annotation

import com.google.gson.{Gson, GsonBuilder}
import org.apache.spark.rdd.RDD

import scala.annotation.StaticAnnotation

object Json {

  type Exclude = ExcludeFromJson

  private val gson = new GsonBuilder()
    .setExclusionStrategies(new ExcludeExcludedFields())
    .create()

  import com.google.gson.{ExclusionStrategy, FieldAttributes}

  private class ExcludeExcludedFields() extends ExclusionStrategy {
    override def shouldSkipClass(clazz: Class[_]): Boolean = false

    override def shouldSkipField(f: FieldAttributes): Boolean = {
      f.getAnnotation(classOf[ExcludeFromJson]) != null
    }
  }

  def serialize(value: Any): String = {
    gson.toJson(value)
  }

  def serialize[T](rdd: RDD[T]): String = {
    serialize(rdd.collect())
  }
}

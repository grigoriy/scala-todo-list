package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

case class Task(id: Long, label: String, userId: String)

object Task {

  def all(userId: String): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task" +
      " where userId = {userId}").on(
        'userId -> userId).as(task *)
  }

  def create(userId: String, label: String): Unit = {
    DB.withConnection { implicit c =>
      SQL("insert into task (userId, label)" +
        " values ({userId, label})").on(
          'userId -> label,
          'label -> label
        ).executeUpdate()
    }
  }

  def delete(userId: String, taskId: Long): Unit = {
    DB.withConnection { implicit c =>
      SQL("delete from task" +
        " where userId = {userId} and id = {taskId}").on(
        'userId -> userId,
        'id -> taskId
      ).executeUpdate()
    }
  }

  val task = {
    get[Long]("id") ~
      get[String]("label") ~
      get[String]("userId") map {
      case id~label~userId => Task(id,label,userId)
    }
  }
}

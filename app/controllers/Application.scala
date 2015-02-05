package controllers

import models.Task
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import securesocial.core._
import service.DemoUser

class Application(override implicit val env: RuntimeEnvironment[DemoUser]) extends securesocial.core.SecureSocial[DemoUser] {

  def index = SecuredAction { implicit request =>
    Redirect(routes.Application.tasks())
  }

  val taskForm = Form("Label" -> nonEmptyText)

  def tasks() = SecuredAction { implicit request =>
    Ok(views.html.index(Task.all(request.user.main.userId), taskForm))
  }

  def newTask() = SecuredAction { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(request.user.main.userId), errors)),
      label => {
        Task.create(request.user.main.userId, label)
        Redirect(routes.Application.tasks())
      }
    )
  }

  def deleteTask(taskId: Long) = SecuredAction { implicit request =>
    Task.delete(request.user.main.userId, taskId)
    Redirect(routes.Application.tasks())
  }
}
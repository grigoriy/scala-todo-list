package controllers

import models.Task
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Redirect(routes.Application.tasks())
  }

  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }

  val taskForm = Form("label" -> nonEmptyText)

  def newTask = TODO

  def deleteTask(id: Long) = TODO
}
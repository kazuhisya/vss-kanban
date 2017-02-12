package module

import application._
import application.impl._
import com.google.inject._
import domain.kanban.KanbanRepository
import domain.user.UserRepository
import infrastructure.kanban.KanbanRepositoryImpl
import infrastructure.user.UserRepositoryImpl

/**
 * DI設定Module.
 */
class BindModule extends AbstractModule {
  /**
   * DI設定定義
   */
  override def configure(): Unit = {
    //traitに対するインスタンスの解決を定義
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl]).in(classOf[Singleton])
    bind(classOf[KanbanRepository]).to(classOf[KanbanRepositoryImpl]).in(classOf[Singleton])

    bind(classOf[MaintenanceService]).to(classOf[MaintenanceServiceImpl]).in(classOf[Singleton])
    bind(classOf[UserSerivce]).to(classOf[UserSerivceImpl]).in(classOf[Singleton])
    bind(classOf[KanbanService]).to(classOf[KanbanServiceImpl]).in(classOf[Singleton])
  }
}

/**
 * DI設定インスタンス管理.
 */
object BindModule {
  val injector: Injector = Guice.createInjector(new BindModule())
}

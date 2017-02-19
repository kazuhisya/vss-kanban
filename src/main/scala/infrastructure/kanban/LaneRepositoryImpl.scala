package infrastructure.kanban

import domain.kanban.{ KanbanId, Lane, LaneId, LaneRepository }
import scalikejdbc.DBSession

/**
 * LaneRepositoryの実装クラス.
 */
class LaneRepositoryImpl extends LaneRepository {
  /**
   * @inheritdoc
   */
  override def findById(laneId: LaneId)(implicit session: DBSession): Option[Lane] = ???

  /**
   * @inheritdoc
   */
  override def findByKanbanId(kanbanId: KanbanId, includeArchive: Boolean)(implicit session: DBSession): Seq[Lane] = ???

  /**
   * @inheritdoc
   */
  override def store(lane: Lane, kanbanId: KanbanId)(implicit session: DBSession): Option[LaneId] = {
    val id = if (lane.laneId.isEmpty) {
      model.Lane.create(model.Lane(
        id = -1L,
        kanbanId = kanbanId.id,
        laneName = lane.configuration.name,
        archiveStatus = lane.configuration.laneStatus.code,
        sortNum = lane.sortNum,
        completeLane = if (lane.configuration.completeLane) "1" else "0"
      ))
    } else {
      -1L
    }
    Option(LaneId(id))
  }

  /**
   * @inheritdoc
   */
  override def findAll(implicit session: DBSession): Seq[Lane] = ???

  /**
   * @inheritdoc
   */
  override def deleteAll()(implicit session: DBSession): Unit = ???
}

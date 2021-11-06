package api.platform.lobby.utils

import api.platform.lobby.model.ProspectiveUser
import recommender.jooq.tables.records.ProspectiveUserRecord

fun ProspectiveUser.toRecord() =
    ProspectiveUserRecord()
        .apply {
            this.name = this@toRecord.name
            this.prospectiveUserId = this@toRecord.prospectiveUserId
        }

fun ProspectiveUserRecord.toProspectiveUser() =
    ProspectiveUser(
        name = this.name,
        prospectiveUserId = this.prospectiveUserId
    )

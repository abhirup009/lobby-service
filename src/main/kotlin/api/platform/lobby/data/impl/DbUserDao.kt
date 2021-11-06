package api.platform.lobby.data.impl

import api.platform.lobby.data.UserDao
import api.platform.lobby.model.ProspectiveUser
import api.platform.lobby.utils.toProspectiveUser
import api.platform.lobby.utils.toRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import recommender.jooq.Tables

@Repository
class DbUserDao(
    private val dslContext: DSLContext
): UserDao {
    override fun newUser(prospectiveUser: ProspectiveUser): ProspectiveUser {
        return dslContext.insertInto(Tables.PROSPECTIVE_USER)
            .set(prospectiveUser.toRecord())
            .returning()
            .fetchOne()
            .toProspectiveUser()
    }

    override fun getUser(prospectiveUserId: String): ProspectiveUser {
        return dslContext.selectFrom(Tables.PROSPECTIVE_USER)
            .where(Tables.PROSPECTIVE_USER.PROSPECTIVE_USER_ID.eq(prospectiveUserId))
            .fetchOne()
            .toProspectiveUser()
    }
}
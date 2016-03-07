
import play.api.mvc.{Action, Controller}

import play.api.data.validation.Constraint

import de.zalando.play.controllers._

import PlayBodyParsing._

import PlayValidations._

import scala.util._



package instagram.api.yaml {

    class InstagramApiYaml extends InstagramApiYamlBase {
        val getmediaByMedia_idLikes = getmediaByMedia_idLikesAction { (media_id: Int) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getmediaByMedia_idLikesAction
        val postmediaByMedia_idLikes = postmediaByMedia_idLikesAction { (media_id: Int) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  postmediaByMedia_idLikesAction
        val deletemediaByMedia_idLikes = deletemediaByMedia_idLikesAction { (media_id: Int) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  deletemediaByMedia_idLikesAction
        val getusersByUser_idFollows = getusersByUser_idFollowsAction { (user_id: Double) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getusersByUser_idFollowsAction
        val getlocationsByLocation_id = getlocationsByLocation_idAction { (location_id: Int) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getlocationsByLocation_idAction
        val getusersSearch = getusersSearchAction { input: (String, MediaFilter) =>
            val (q, count) = input
            

            Failure(???)

            

        } //////// EOF ////////  getusersSearchAction
        val getusersSelfMediaLiked = getusersSelfMediaLikedAction { input: (MediaId, MediaId) =>
            val (count, max_like_id) = input
            

            Failure(???)

            

        } //////// EOF ////////  getusersSelfMediaLikedAction
        val gettagsByTag_name = gettagsByTag_nameAction { (tag_name: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  gettagsByTag_nameAction
        val gettagsSearch = gettagsSearchAction { (q: MediaFilter) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  gettagsSearchAction
        val getusersByUser_idFollowed_by = getusersByUser_idFollowed_byAction { (user_id: Double) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getusersByUser_idFollowed_byAction
        val getmediaByMedia_idComments = getmediaByMedia_idCommentsAction { (media_id: Int) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getmediaByMedia_idCommentsAction
        val postmediaByMedia_idComments = postmediaByMedia_idCommentsAction { input: (Int, LocationLatitude) =>
            val (media_id, tEXT) = input
            

            Failure(???)

            

        } //////// EOF ////////  postmediaByMedia_idCommentsAction
        val deletemediaByMedia_idComments = deletemediaByMedia_idCommentsAction { (media_id: Int) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  deletemediaByMedia_idCommentsAction
        val gettagsByTag_nameMediaRecent = gettagsByTag_nameMediaRecentAction { (tag_name: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  gettagsByTag_nameMediaRecentAction
        val postusersByUser_idRelationship = postusersByUser_idRelationshipAction { input: (Double, MediaFilter) =>
            val (user_id, action) = input
            

            Failure(???)

            

        } //////// EOF ////////  postusersByUser_idRelationshipAction
        val getusersSelfFeed = getusersSelfFeedAction { input: (MediaId, MediaId, MediaId) =>
            val (count, max_id, min_id) = input
            

            Failure(???)

            

        } //////// EOF ////////  getusersSelfFeedAction
        val getusersByUser_id = getusersByUser_idAction { (user_id: Double) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getusersByUser_idAction
        val getmediaSearch = getmediaSearchAction { input: (MediaId, Int, LocationLatitude, MediaId, LocationLatitude) =>
            val (mAX_TIMESTAMP, dISTANCE, lNG, mIN_TIMESTAMP, lAT) = input
            

            Failure(???)

            

        } //////// EOF ////////  getmediaSearchAction
        val getgeographiesByGeo_idMediaRecent = getgeographiesByGeo_idMediaRecentAction { input: (Int, MediaId, MediaId) =>
            val (geo_id, count, min_id) = input
            

            Failure(???)

            

        } //////// EOF ////////  getgeographiesByGeo_idMediaRecentAction
        val getmediaByShortcode = getmediaByShortcodeAction { (shortcode: String) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getmediaByShortcodeAction
        val getlocationsSearch = getlocationsSearchAction { input: (MediaId, MediaId, MediaId, LocationLatitude, MediaId, LocationLatitude) =>
            val (foursquare_v2_id, facebook_places_id, distance, lat, foursquare_id, lng) = input
            

            Failure(???)

            

        } //////// EOF ////////  getlocationsSearchAction
        val getusersSelfRequested_by = getusersSelfRequested_byAction { _ =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getusersSelfRequested_byAction
        val getmediaByMedia_id = getmediaByMedia_idAction { (media_id: Int) =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getmediaByMedia_idAction
        val getlocationsByLocation_idMediaRecent = getlocationsByLocation_idMediaRecentAction { input: (Int, MediaId, MediaId, MediaFilter, MediaFilter) =>
            val (location_id, max_timestamp, min_timestamp, min_id, max_id) = input
            

            Failure(???)

            

        } //////// EOF ////////  getlocationsByLocation_idMediaRecentAction
        val getusersByUser_idMediaRecent = getusersByUser_idMediaRecentAction { input: (Double, MediaId, MediaFilter, MediaId, MediaFilter, MediaId) =>
            val (user_id, max_timestamp, min_id, min_timestamp, max_id, count) = input
            

            Failure(???)

            

        } //////// EOF ////////  getusersByUser_idMediaRecentAction
        val getmediaPopular = getmediaPopularAction { _ =>
            
            

            Failure(???)

            

        } //////// EOF ////////  getmediaPopularAction
    }
}
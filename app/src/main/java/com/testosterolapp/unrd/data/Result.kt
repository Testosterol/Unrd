package com.testosterolapp.unrd.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Result {


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    // Primitive rows
    var story_id: Long? = null
    var language_id: Long? = null
    var genre_id: Long? = null
    var main_character_id: Long? = null
    var story_name: String? = null
    var short_summary: String? = null
    var full_summary: String? = null
    var duration: String? = null
    var price: Long? = null
    var age_from: Long? = null
    var age_to: Long? = null
    var intro_video_sequence: Long? = null
    var story_start_sequence: Long? = null
    var story_end_sequence: Long? = null
    var passcode_clue: Boolean? = null
    var passcode_value: Long? = null
    var is_coming_soon: Boolean? = null
    var is_in_testing: Boolean? = null
    var is_early_access: Boolean? = null
    var is_published: Boolean? = null
    var is_featured: Boolean? = null
    var release_date: String? = null
    var release_timezone: String? = null
    var created: String? = null
    var updated: String? = null
    var publication_status: String? = null
    var is_owned: Boolean? = null
    var purchase_date: String? = null
    var last_progress_report: String? = null
    var progress: Long? = null

    // Nested tables
    @Embedded
    var list_image: ListImage? = null
    @Embedded
    var preview_media: PreviewMedia? = null
    @Embedded
    var intro_video: IntroVideo? = null
    @Embedded
    var background_image: BackgroundImage? = null
    @Embedded
    var characters: Characters? = null
    @Embedded
    var timelines: Timelines? = null
    @Embedded
    var purchased_items: PurchasedItems? = null
    @Embedded
    var contacts: Contacts? = null


    constructor(story_id: Long?, language_id: Long?, genre_id: Long?, main_character_id: Long?,
                story_name: String?, short_summary: String?, full_summary: String?,
                duration: String?, price: Long?, age_from: Long?, age_to: Long?,
                intro_video_sequence: Long?, story_start_sequence: Long?, story_end_sequence: Long?,
                passcode_clue: Boolean?, passcode_value: Long?, is_coming_soon: Boolean?,
                is_in_testing: Boolean?, is_early_access: Boolean?, is_published: Boolean?,
                is_featured: Boolean?, release_date: String?, release_timezone: String?,
                created: String?, updated: String?, publication_status: String?, is_owned: Boolean?,
                purchase_date: String?, last_progress_report: String?, progress: Long?,
                list_image: ListImage?, preview_media: PreviewMedia?, intro_video: IntroVideo?,
                background_image: BackgroundImage?, characters: Characters?, timelines: Timelines?,
                purchased_items: PurchasedItems?, contacts: Contacts?) {
        this.story_id = story_id
        this.language_id = language_id
        this.genre_id = genre_id
        this.main_character_id = main_character_id
        this.story_name = story_name
        this.short_summary = short_summary
        this.full_summary = full_summary
        this.duration = duration
        this.price = price
        this.age_from = age_from
        this.age_to = age_to
        this.intro_video_sequence = intro_video_sequence
        this.story_start_sequence = story_start_sequence
        this.story_end_sequence = story_end_sequence
        this.passcode_clue = passcode_clue
        this.passcode_value = passcode_value
        this.is_coming_soon = is_coming_soon
        this.is_in_testing = is_in_testing
        this.is_early_access = is_early_access
        this.is_published = is_published
        this.is_featured = is_featured
        this.release_date = release_date
        this.release_timezone = release_timezone
        this.created = created
        this.updated = updated
        this.publication_status = publication_status
        this.is_owned = is_owned
        this.purchase_date = purchase_date
        this.last_progress_report = last_progress_report
        this.progress = progress
        this.list_image = list_image
        this.preview_media = preview_media
        this.intro_video = intro_video
        this.background_image = background_image
        this.characters = characters
        this.timelines = timelines
        this.purchased_items = purchased_items
        this.contacts = contacts
    }



}
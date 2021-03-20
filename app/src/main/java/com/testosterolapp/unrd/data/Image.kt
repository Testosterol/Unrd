package com.testosterolapp.unrd.data

class Image {

    var resource_id: Int? = 0
    var resource_fid: String? = null
    var resource_type: String? = null
    var resource_uri: String? = null
    var resource_preset: String? = null
    var resource_processed: Boolean? = false
    var resource_progress: Int? = null

    constructor(resource_id: Int?, resource_fid: String?, resource_type: String?,
                resource_uri: String?, resource_preset: String?, resource_processed: Boolean?,
                resource_progress: Int?) {
        this.resource_id = resource_id
        this.resource_fid = resource_fid
        this.resource_type = resource_type
        this.resource_uri = resource_uri
        this.resource_preset = resource_preset
        this.resource_processed = resource_processed
        this.resource_progress = resource_progress
    }
}
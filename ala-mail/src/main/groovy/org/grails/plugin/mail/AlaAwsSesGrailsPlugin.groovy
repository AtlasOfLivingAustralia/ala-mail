package org.grails.plugin.mail

import au.org.ala.mail.AlaAwsSesConfiguration

import grails.plugins.Plugin

class AlaAwsSesGrailsPlugin extends Plugin {
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "6.2.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = []

    def title = "ALA AWS SES Plugin" // Headline display name of the plugin
    def author = "Atlas of Living Australia"
    def authorEmail = ""
    def description = "Grails plugin setups the Grails Mail Plugin to use AWS SES as the mail sender."

    def profiles = ['web', 'rest-api']

    // URL to the plugin's documentation
    def documentation = "https://github.com/AtlasOfLivingAustralia/ala-mail"

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "MPL-2.0"

    // Details of company behind the plugin (if there is one)
    def organization = [name: "Atlas of Living Australia", url: "http://ala.org.au"]

    def doWithWebDescriptor = { xml ->
    }

    Closure doWithSpring() {
        { ->
            alaAwsSesConfiguration(au.org.ala.mail.AlaAwsSesConfiguration)
        }
    }
}

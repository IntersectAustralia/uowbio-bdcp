

<%@ page import="au.org.intersect.bdcp.Device" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'device.label', default: 'Device')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${deviceInstance}">
            <div class="errors">
                <g:renderErrors bean="${deviceInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="device.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" cols="40" rows="5" value="${deviceInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="device.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" cols="40" rows="5" value="${deviceInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="manufacturer"><g:message code="device.manufacturer.label" default="Manufacturer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'manufacturer', 'errors')}">
                                    <g:textField name="manufacturer" cols="40" rows="5" value="${deviceInstance?.manufacturer}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locationOfManufacturer"><g:message code="device.locationOfManufacturer.label" default="Location Of Manufacturer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'locationOfManufacturer', 'errors')}">
                                    <g:textField name="locationOfManufacturer" cols="40" rows="5" value="${deviceInstance?.locationOfManufacturer}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="model"><g:message code="device.model.label" default="Model" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'model', 'errors')}">
                                    <g:textField name="model" cols="40" rows="5" value="${deviceInstance?.model}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="serialNumber"><g:message code="device.serialNumber.label" default="Serial Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'serialNumber', 'errors')}">
                                    <g:textField name="serialNumber" cols="40" rows="5" value="${deviceInstance?.serialNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateOfPurchase"><g:message code="device.dateOfPurchase.label" default="Date Of Purchase" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'dateOfPurchase', 'errors')}">
                                    <g:datePicker name="dateOfPurchase" precision="day" value="${deviceInstance?.dateOfPurchase}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateOfDelivery"><g:message code="device.dateOfDelivery.label" default="Date Of Delivery" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'dateOfDelivery', 'errors')}">
                                    <g:datePicker name="dateOfDelivery" precision="day" value="${deviceInstance?.dateOfDelivery}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="purchasePrice"><g:message code="device.purchasePrice.label" default="Purchase Price" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'purchasePrice', 'errors')}">
                                    <g:textField name="purchasePrice" cols="40" rows="5" value="${deviceInstance?.purchasePrice}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="vendor"><g:message code="device.vendor.label" default="Vendor" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'vendor', 'errors')}">
                                    <g:textField name="vendor" cols="40" rows="5" value="${deviceInstance?.vendor}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fundingBody"><g:message code="device.fundingBody.label" default="Funding Body" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'fundingBody', 'errors')}">
                                    <g:textField name="fundingBody" cols="40" rows="5" value="${deviceInstance?.fundingBody}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="deviceGroup"><g:message code="device.deviceGroup.label" default="Device Group" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'deviceGroup', 'errors')}">
                                    <g:select name="deviceGroup.id" from="${au.org.intersect.bdcp.DeviceGroup.list()}" optionKey="id" value="${deviceInstance?.deviceGroup?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Save')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

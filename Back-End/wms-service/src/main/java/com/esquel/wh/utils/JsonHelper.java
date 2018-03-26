package com.esquel.wh.utils;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JsonHelper {
	static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonHelper()
    {
    }

    public static JsonNode jsonNode( String json ) throws IOException
    {
        try
        {
            return OBJECT_MAPPER.readTree( json );
        }
        catch ( IOException e )
        {
            throw e ;
        }
    }

    @SuppressWarnings( "unchecked" )
    public static Map<String, Object> jsonToMap( String json ) throws Exception
    {
        return (Map<String, Object>) readJson( json );
    }

    @SuppressWarnings( "unchecked" )
    public static List<Map<String, Object>> jsonToList( String json ) throws Exception
    {
        return (List<Map<String, Object>>) readJson( json );
    }

    public static Object readJson( String json ) throws Exception
    {
        try
        {
            return OBJECT_MAPPER.readValue( json, Object.class );
        }
        catch ( org.codehaus.jackson.JsonParseException e )
        {
            String message = e.getMessage().split( "\\r?\\n" )[0];
            JsonLocation location = e.getLocation();
            throw new JsonParseException( String.format( "%s [line: %d, column: %d]", message, location.getLineNr(),
                    location.getColumnNr() ), location );
        }
        catch ( IOException e )
        {
            throw new Exception( e );
        }
    }

    public static Object assertSupportedPropertyValue( Object jsonObject ) throws Exception 
    {
        if ( jsonObject instanceof Collection<?> )
        {
            return jsonObject;
        }
        if ( jsonObject == null )
        {
            throw new Exception( "null value not supported" );
        }
        if ( !(jsonObject instanceof String ||
               jsonObject instanceof Number ||
               jsonObject instanceof Boolean) )
        {
            throw new Exception(
                    "Unsupported value type " + jsonObject.getClass() + "."
                    + " Supported value types are all java primitives (byte, char, short, int, "
                    + "long, float, double) and String, as well as arrays of all those types" );
        }
        return jsonObject;
    }

    public static String createJsonFrom( Object data ) throws Exception 
    {
        try
        {
            StringWriter writer = new StringWriter();
            try
            {
                JsonGenerator generator = OBJECT_MAPPER.getJsonFactory()
                    .createJsonGenerator( writer )
                    .useDefaultPrettyPrinter();
                writeValue( generator, data );
            }
            finally
            {
                writer.close();
            }
            return writer.getBuffer().toString();
        }
        catch ( IOException e )
        {
            throw new Exception( e );
        }
    }

    public static void writeValue( JsonGenerator jgen, Object value ) throws IOException
    {
        OBJECT_MAPPER.writeValue( jgen, value );
    }

    public static String prettyPrint( Object item ) throws IOException
    {
        return OBJECT_MAPPER.writer().withDefaultPrettyPrinter().writeValueAsString( item );
    }
}

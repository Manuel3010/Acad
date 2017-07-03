/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

/**
 *
 * @author sigfrid
 */
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author Arun Gupta
 */
public class FigureEncoder implements Encoder.Text<Figure> {

    private static final Logger LOGGER = Logger.getLogger(FigureDecoder.class.getName());

    @Override
    public String encode(Figure figure) throws EncodeException {
        LOGGER.log(Level.INFO, "encoding: {0}", figure);
        return figure.getJson().toString();
    }

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }
}


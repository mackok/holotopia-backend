package nhl.stenden.util;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperUtil extends UtilClass {

    public static <D, T> List<D> mapAll(final Collection<T> list, Class<D> outClass, ModelMapper modelMapper){
        return list.stream()
                .map(entity -> modelMapper.map(entity, outClass))
                .collect(Collectors.toList());
    }
}
